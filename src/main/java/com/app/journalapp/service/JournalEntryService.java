package com.app.journalapp.service;

import com.app.journalapp.dto.JournalEntryRequestDto;
import com.app.journalapp.entity.JournalEntry;
import com.app.journalapp.entity.Users;
import com.app.journalapp.repo.JournalAppRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public JournalEntry saveJournalEntry(JournalEntryRequestDto journalEntry) {
        try {
            JournalEntry journalEntry1 = new JournalEntry();
            journalEntry1.setDate(LocalDateTime.now());
            journalEntry1.setTitle(journalEntry.getTitle());
            journalEntry1.setContent(journalEntry.getContent());
            Users user = userService.findByUsername(journalEntry.getUsername());
            journalEntry1.setUser(user);
            JournalEntry savedJournalEntry = journalAppRepo.save(journalEntry1);
            user.getJournalEntries().add(savedJournalEntry);
            userService.saveUser(user);
            return savedJournalEntry;
        } catch (Exception e) {
            log.error("Error saving journal entry", e);
            throw e;
        }

    }

    public Optional<JournalEntry> getJournalEntryById(long id) {
        return journalAppRepo.findById(id);
    }

    @Transactional
    public void deleteJournalEntry(long id) {
        Optional<JournalEntry> journalEntryOptional = getJournalEntryById(id);
        if (journalEntryOptional.isPresent()) {
            JournalEntry journalEntry = journalEntryOptional.get();
            journalEntry.getUser().getJournalEntries().remove(journalEntry);
            userService.saveUser(journalEntry.getUser());
        }
        journalAppRepo.deleteById(id);
    }

    @Transactional
    public JournalEntry updateJournalEntry(long id, JournalEntryRequestDto journalEntry) {
        Optional<JournalEntry> journalEntry1 = getJournalEntryById(id);
        if (journalEntry1.isPresent()) {
            if (journalEntry.getTitle() != null && !journalEntry.getTitle().isEmpty()) {
                journalEntry1.get().setTitle(journalEntry.getTitle());
            } else {
                journalEntry1.get().setTitle(journalEntry1.get().getTitle());
            }
            journalEntry1.get().setContent(journalEntry.getContent() != null && !journalEntry.getContent().isEmpty() ? journalEntry.getContent() : journalEntry1.get().getContent());
            journalEntry1.get().setDate(LocalDateTime.now());
            journalAppRepo.save(journalEntry1.get());
        }
        return journalEntry1.orElse(null);
    }

    public List<JournalEntry> getAllJournalEntries(String username) {
        Users user = userService.findByUsername(username);
        if (user != null) {
            return user.getJournalEntries();
        }
        return new java.util.ArrayList<>();
    }

}
