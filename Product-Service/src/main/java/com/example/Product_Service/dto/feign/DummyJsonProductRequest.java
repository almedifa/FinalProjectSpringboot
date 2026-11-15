package com.example.Product_Service.dto.feign;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DummyJsonProductRequest {
    private String title;
    private BigDecimal price;
    private String category;
    private String brand;
}
