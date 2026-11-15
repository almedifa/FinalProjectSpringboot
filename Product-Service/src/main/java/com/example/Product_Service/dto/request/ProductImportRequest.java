package com.example.Product_Service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductImportRequest {
    @NotNull(message = "sourceId wajib diisi")
    private Long sourceId;

    @NotBlank(message = "nama wajib diisi")
    private String nama;

    @NotNull(message = "harga wajib diisi")
    @Positive(message = "harga harus lebih besar dari nol")
    private BigDecimal harga;

    @NotBlank(message = "kategori wajib diisi")
    private String kategori;

    @NotBlank(message = "brand wajib diisi")
    private String brand;

    @NotNull(message = "rating wajib diisi")
    private BigDecimal rating;
}
