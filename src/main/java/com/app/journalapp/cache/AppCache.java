package com.app.journalapp.cache;

import com.app.journalapp.entity.ConfigJournalAppEntity;
import com.app.journalapp.repo.ConfigJournalAppRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API
    }

    @Autowired
    private ConfigJournalAppRepo configJournalAppRepo;

    public Map<String, String> appCache;

    @PostConstruct
    public void init() {
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> configJournalAppEntities = configJournalAppRepo.findAll();
//        appCache = configJournalAppEntities.stream().collect(Collectors.toMap(ConfigJournalAppEntity::getKey, ConfigJournalAppEntity::getValue));
        configJournalAppEntities.forEach(configJournalAppEntity -> appCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue()));
    }
}
