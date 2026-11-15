package com.example.scheduler_service.scheduler;

import com.example.scheduler_service.service.ProductImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class ProductImportScheduler {
    private final ProductImportService productImportService;

    @Scheduled(cron = "0 */2 * * * *")
    public void importEveryTwoMinutes() {
        productImportService.importNextBatch();
    }
}
