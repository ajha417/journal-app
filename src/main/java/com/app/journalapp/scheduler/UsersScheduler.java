package com.app.journalapp.scheduler;

import com.app.journalapp.cache.AppCache;
import com.app.journalapp.entity.JournalEntry;
import com.app.journalapp.entity.Users;
import com.app.journalapp.model.SentimentData;
import com.app.journalapp.repo.UserRepoImpl;
import com.app.journalapp.service.SentimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsersScheduler {

    @Autowired
    private UserRepoImpl userRepoImpl;

    @Autowired
    private SentimentService sentimentService;

    @Autowired
    private AppCache appCache;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    public List<Users> fetchUsersWithSentimentAnalysis() {
        return userRepoImpl.getUsersWithSentimentAnalysis();
    }

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendMail() {
           List<Users> users = fetchUsersWithSentimentAnalysis();
           for (Users user : users) {
               List<JournalEntry> journalEntries = user.getJournalEntries();
               List<String> filteredContent = journalEntries.stream().
                       filter(journalEntry -> journalEntry.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(JournalEntry::getContent).
                       collect((Collectors.toList()));
               String entry = String.join(" ", filteredContent);
               String sentiment = sentimentService.getSentiment(entry);

               //sending data via kafka-template
               SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment(sentiment).build();
               kafkaTemplate.send("weekly-sentiment-analysis", sentimentData.getEmail(), sentimentData);
           }
    }

    @Scheduled(cron = "0 0/10 * * * *")
    public void clearAppCache() {
        appCache.init();
    }
}
