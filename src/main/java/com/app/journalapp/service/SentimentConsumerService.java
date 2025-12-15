package com.app.journalapp.service;

import com.app.journalapp.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "weekly-sentiment-analysis", groupId = "group_id")
    public void consume(SentimentData sentimentData) {
        sendEmail(sentimentData);
    }

    private void sendEmail(SentimentData sentimentData) {
        emailService.sendEmail(sentimentData.getEmail(), "Weekly Sentiment Analysis", sentimentData.getSentiment());
    }
}
