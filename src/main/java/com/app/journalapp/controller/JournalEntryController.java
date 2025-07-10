package com.app.journalapp.controller;

import com.app.journalapp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping("/entries")
    public List<JournalEntry> getJournalEntries() {
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping("/addEntry")
    public boolean createEntry(@RequestBody JournalEntry journalEntry) {
        journalEntries.put(journalEntry.getId(), journalEntry);
        return true;
    }

    @GetMapping( "/getEntryById/{id}")
    public JournalEntry getEntryById(@PathVariable("id") long id) {
        return journalEntries.get(id);
    }

    @DeleteMapping("/deleteEntry/{id}")
    public boolean deleteEntry(@PathVariable("id") long id) {
        journalEntries.remove(id);
        return true;
    }

    @PutMapping("/updateEntry/{id}")
    public boolean updateEntry(@PathVariable long id, @RequestBody JournalEntry journalEntry) {
        journalEntries.put(id, journalEntry);
        return true;
    }
}
