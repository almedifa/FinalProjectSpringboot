package com.example.scheduler_service.scheduler;

import com.example.scheduler_service.service.ProductImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class ProductImportStartupRunner implements ApplicationRunner {
    private final ProductImportService productImportService;

    @Override
    public void run(ApplicationArguments args) {
        productImportService.importNextBatch();
    }
}
