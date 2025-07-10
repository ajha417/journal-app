package com.app.journalapp.repo;

import com.app.journalapp.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalAppRepo extends JpaRepository<JournalEntry, Long> {
}
