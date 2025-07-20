package com.app.journalapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JournalEntryRequestDto {
    private String title;
    private String content;
    private String username;
}
