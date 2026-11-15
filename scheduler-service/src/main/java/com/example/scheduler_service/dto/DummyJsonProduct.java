package com.example.scheduler_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DummyJsonProduct {
    private Long id;
    private String title;
    private BigDecimal price;
    private String category;
    private String brand;
    private BigDecimal rating;
}
