package com.example.scheduler_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImportSummary {
    private boolean successful;
    private int fetched;
    private int imported;
    private int skippedLowRating;
    private int duplicates;
    private int nextSkip;
    private String message;
}
