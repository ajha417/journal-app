package com.app.journalapp.controller;

import com.app.journalapp.entity.JournalEntry;
import com.app.journalapp.service.JournalEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
public class JournalEntryController {


    public final JournalEntryService journalEntryService;

    @GetMapping("/entries")
    public List<JournalEntry> getJournalEntries() {
        return journalEntryService.getAllJournalEntries();
    }

    @PostMapping("/addEntry")
    public boolean createEntry(@RequestBody JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryService.saveJournalEntry(journalEntry);
        return true;
    }

    @GetMapping( "/getEntryById/{id}")
    public JournalEntry getEntryById(@PathVariable("id") long id) {
        return journalEntryService.getJournalEntryById(id);
    }

    @DeleteMapping("/deleteEntry/{id}")
    public boolean deleteEntry(@PathVariable("id") long id) {
        journalEntryService.deleteJournalEntry(id);
        return true;
    }

    @PutMapping("/updateEntry/{id}")
    public JournalEntry updateEntry(@PathVariable long id, @RequestBody JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        return journalEntryService.updateJournalEntry(id, journalEntry);
    }
}
