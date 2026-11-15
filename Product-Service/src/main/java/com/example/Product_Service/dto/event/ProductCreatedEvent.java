package com.example.Product_Service.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProductCreatedEvent {
    private Long productId;
    private String nama;
    private BigDecimal harga;
    private String kategori;
    private String brand;
    private LocalDateTime dateAdded;
}
