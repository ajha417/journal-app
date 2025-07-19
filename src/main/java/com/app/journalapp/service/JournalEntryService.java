package com.app.journalapp.service;

import com.app.journalapp.entity.JournalEntry;
import com.app.journalapp.repo.JournalAppRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JournalEntryService {

    private final JournalAppRepo journalAppRepo;

    public JournalEntry saveJournalEntry(JournalEntry journalEntry) {
        return journalAppRepo.save(journalEntry);
    }

    public Optional<JournalEntry> getJournalEntryById(long id) {
        return journalAppRepo.findById(id);
    }

    public void deleteJournalEntry(long id) {
        journalAppRepo.deleteById(id);
    }

    public JournalEntry updateJournalEntry(long id, JournalEntry journalEntry) {
        Optional<JournalEntry> journalEntry1 = getJournalEntryById(id);
        if (journalEntry1.isPresent()) {
            if (journalEntry.getTitle() != null && !journalEntry.getTitle().isEmpty()) {
                journalEntry1.get().setTitle(journalEntry.getTitle());
            } else {
                journalEntry1.get().setTitle(journalEntry1.get().getTitle());
            }
            journalEntry1.get().setContent(journalEntry.getContent() != null && !journalEntry.getContent().isEmpty() ? journalEntry.getContent() : journalEntry1.get().getContent());
            journalEntry1.get().setDate(journalEntry.getDate());
            journalAppRepo.save(journalEntry1.get());
        }
        return journalEntry1.orElse(null);
    }

    public List<JournalEntry> getAllJournalEntries() {
        return journalAppRepo.findAll();
    }

}
