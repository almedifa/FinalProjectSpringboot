package com.example.scheduler_service.controller;

import com.example.scheduler_service.dto.BaseResponse;
import com.example.scheduler_service.dto.ImportSummary;
import com.example.scheduler_service.service.ProductImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler")
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class SchedulerController {
    private final ProductImportService productImportService;

    @PostMapping("/import")
    public ResponseEntity<BaseResponse<ImportSummary>> importManually() {
        ImportSummary summary = productImportService.importNextBatch();
        if (!summary.isSuccessful()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(BaseResponse.error(summary.getMessage()));
        }
        return ResponseEntity.ok(BaseResponse.ok(summary.getMessage(), summary));
    }
}
