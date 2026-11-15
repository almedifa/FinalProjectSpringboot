package com.example.scheduler_service.service;

import com.example.scheduler_service.dto.ImportSummary;

public interface ProductImportService {
    ImportSummary importNextBatch();
}
