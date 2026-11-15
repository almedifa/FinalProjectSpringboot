package com.example.Product_Service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequest {
    @NotBlank(message = "nama wajib diisi")
    @Size(max = 200, message = "nama maksimal 200 karakter")
    private String nama;

    @NotNull(message = "harga wajib diisi")
    @Positive(message = "harga harus lebih besar dari nol")
    private BigDecimal harga;

    @NotBlank(message = "kategori wajib diisi")
    @Size(max = 100, message = "kategori maksimal 100 karakter")
    private String kategori;

    @NotBlank(message = "brand wajib diisi")
    @Size(max = 100, message = "brand maksimal 100 karakter")
    private String brand;
}
