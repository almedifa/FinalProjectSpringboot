package com.example.Product_Service.dto.feign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DummyJsonProductResponse {
    private Long id;
    private String title;
    private BigDecimal price;
    private String category;
    private String brand;
    private Boolean isDeleted;
    private LocalDateTime deletedOn;
}
