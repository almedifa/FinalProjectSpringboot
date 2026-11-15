package com.example.scheduler_service.feign;

import com.example.scheduler_service.dto.BaseResponse;
import com.example.scheduler_service.dto.ProductImportRequest;
import com.example.scheduler_service.dto.ProductImportResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Service name is resolved through Eureka; there is intentionally no fixed host or port.
@FeignClient(name = "product-service")
public interface ProductServiceClient {
    @PostMapping("/product/internal/import")
    BaseResponse<ProductImportResult> importProduct(@RequestBody ProductImportRequest request);
}
