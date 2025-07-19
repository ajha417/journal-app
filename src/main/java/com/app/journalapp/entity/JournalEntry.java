package com.app.journalapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@Entity(name = "journal_entries")
@AllArgsConstructor
@NoArgsConstructor
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
    @JoinColumn(name = "user_id")
    @ManyToOne
    private Users user;
}
