package com.app.journalapp.service;

import com.app.journalapp.entity.JournalEntry;
import com.app.journalapp.repo.JournalAppRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JournalEntryService {

    private final JournalAppRepo journalAppRepo;

    public void saveJournalEntry(JournalEntry journalEntry) {
        journalAppRepo.save(journalEntry);
    }

    public JournalEntry getJournalEntryById(long id) {
        Optional<JournalEntry> journalEntry = journalAppRepo.findById(id);
        return journalEntry.orElse(null);
    }

    public void deleteJournalEntry(long id) {
        journalAppRepo.deleteById(id);
    }

    public JournalEntry updateJournalEntry(long id, JournalEntry journalEntry) {
        JournalEntry journalEntry1 = getJournalEntryById(id);
        if (journalEntry1 != null) {
            if (journalEntry.getTitle() != null && !journalEntry.getTitle().isEmpty()) {
                journalEntry1.setTitle(journalEntry.getTitle());
            } else {
                journalEntry1.setTitle(journalEntry1.getTitle());
            }
            journalEntry1.setContent(journalEntry.getContent() != null && !journalEntry.getContent().isEmpty() ? journalEntry.getContent() : journalEntry1.getContent());
            journalEntry1.setDate(journalEntry.getDate());
            journalAppRepo.save(journalEntry1);
        }

        return journalEntry1;
    }

    public List<JournalEntry> getAllJournalEntries() {
        return journalAppRepo.findAll();
    }

}
