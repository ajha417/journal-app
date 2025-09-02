package com.app.journalapp.repo;

import com.app.journalapp.entity.ConfigJournalAppEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigJournalAppRepo extends JpaRepository<ConfigJournalAppEntity, Long> {
}
