package com.example.Product_Service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductImportResult {
    private String outcome;
    private Long productId;
    private String message;
}
