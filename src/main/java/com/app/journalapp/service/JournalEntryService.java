package com.app.journalapp.service;

import com.app.journalapp.entity.JournalEntry;
import com.app.journalapp.entity.Users;
import com.app.journalapp.repo.JournalAppRepo;
import com.app.journalapp.utils.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JournalEntryService {

    private final JournalAppRepo journalAppRepo;
    private final UserService userService;

    @Transactional
    public JournalEntry saveJournalEntry(JournalEntry journalEntry) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Users user = userService.findByUsername(username);
            journalEntry.setUser(user);
            JournalEntry savedJournalEntry = journalAppRepo.save(journalEntry);
            user.getJournalEntries().add(savedJournalEntry);
            userService.saveUser(user);
            return savedJournalEntry;
        } catch (Exception e) {
            log.error("Error saving journal entry", e);
            throw e;
        }

    }

    public Optional<JournalEntry> getJournalEntryById(long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userService.findByUsername(username);
        return user.getJournalEntries().stream().filter(journalEntry1 -> journalEntry1.getId() == id).findFirst();
    }

    @Transactional
    public void deleteJournalEntry(long id) {
        Optional<JournalEntry> journalEntryOptional = getJournalEntryById(id);
        if (journalEntryOptional.isPresent()) {
            JournalEntry journalEntry = journalEntryOptional.get();
            journalEntry.getUser().getJournalEntries().remove(journalEntry);
            userService.saveUser(journalEntry.getUser());
        } else throw new IllegalArgumentException("Journal entry not found with id: " + id);
        journalAppRepo.deleteById(id);
    }

    @Transactional
    public JournalEntry updateJournalEntry(long id, JournalEntry journalEntry) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userService.findByUsername(username);
        Optional<JournalEntry> journalEntryOptional = user.getJournalEntries().stream().filter(journalEntry1 -> journalEntry1.getId() == id).findFirst();
        if (journalEntryOptional.isPresent()) {
            JournalEntry oldJournalEntry = journalEntryOptional.get();
            if(!StringUtils.isNullOrEmpty(journalEntry.getTitle()) && !journalEntry.getTitle().equals(oldJournalEntry.getTitle())) {
                oldJournalEntry.getUser().getJournalEntries().remove(oldJournalEntry);
                oldJournalEntry.getUser().getJournalEntries().add(journalEntry);
                userService.saveUser(oldJournalEntry.getUser());
                oldJournalEntry.setTitle(journalEntry.getTitle());
                oldJournalEntry.setContent(journalEntry.getContent());
                oldJournalEntry.setDate(LocalDateTime.now());
                journalAppRepo.save(oldJournalEntry);
                return oldJournalEntry;
            }
        }
        else{
            throw new IllegalArgumentException("Journal entry not found with id: " + id);
        }
        return journalEntry;
    }

    public List<JournalEntry> getAllJournalEntries(String username) {
        Users user = userService.findByUsername(username);
        if (user != null) {
            return user.getJournalEntries();
        }
        return new java.util.ArrayList<>();
    }

}
