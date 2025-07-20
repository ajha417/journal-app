package com.app.journalapp.controller;

import com.app.journalapp.dto.JournalEntryRequestDto;
import com.app.journalapp.entity.JournalEntry;
import com.app.journalapp.service.JournalEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
public class JournalEntryController {


    public final JournalEntryService journalEntryService;

    @GetMapping("/entries/{username}")
    public ResponseEntity<?> getJournalEntriesOfUser(@PathVariable String username) {
        List<JournalEntry> journalEntries = journalEntryService.getAllJournalEntries(username);
        return journalEntries.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(journalEntries, HttpStatus.OK);
    }

    @PostMapping("/addEntry")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntryRequestDto journalEntry) {
        try {
            return new ResponseEntity<>(journalEntryService.saveJournalEntry(journalEntry), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping( "/getEntryById/{id}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable("id") long id) {
        Optional<JournalEntry> journalEntryOptional = journalEntryService.getJournalEntryById(id);
        return journalEntryOptional.map(journalEntry -> new ResponseEntity<>(journalEntry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/deleteEntry/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable("id") long id) {
        journalEntryService.deleteJournalEntry(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/updateEntry/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable long id, @RequestBody JournalEntryRequestDto journalEntry) {
        JournalEntry journalEntry1 = journalEntryService.updateJournalEntry(id, journalEntry);
        if (journalEntry1 != null) {
            return new ResponseEntity<>(journalEntry1, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
