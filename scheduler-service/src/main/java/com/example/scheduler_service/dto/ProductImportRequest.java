package com.example.scheduler_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImportRequest {
    private Long sourceId;
    private String nama;
    private BigDecimal harga;
    private String kategori;
    private String brand;
    private BigDecimal rating;
}
