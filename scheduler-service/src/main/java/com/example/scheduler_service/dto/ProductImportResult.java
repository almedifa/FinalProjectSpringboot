package com.example.scheduler_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductImportResult {
    private String outcome;
    private Long productId;
    private String message;
}
