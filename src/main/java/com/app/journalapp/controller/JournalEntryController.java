package com.app.journalapp.controller;

import com.app.journalapp.dto.JournalEntryRequestDto;
import com.app.journalapp.entity.JournalEntry;
import com.app.journalapp.service.JournalEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
public class JournalEntryController {


    public final JournalEntryService journalEntryService;

    @GetMapping("/entries")
    public ResponseEntity<?> getJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<JournalEntry> journalEntries = journalEntryService.getAllJournalEntries(username);
        return journalEntries.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(journalEntries, HttpStatus.OK);
    }

    @PostMapping("/addEntry")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
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
        try {
            journalEntryService.deleteJournalEntry(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateEntry/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable long id, @RequestBody JournalEntry journalEntry) {
        try {
            JournalEntry journalEntry1 = journalEntryService.updateJournalEntry(id, journalEntry);
            if (journalEntry1 != null) {
                return new ResponseEntity<>(journalEntry1, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
