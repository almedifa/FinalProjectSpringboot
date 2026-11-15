package com.example.Product_Service.dto.response;

import com.example.Product_Service.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDateTime;

// DTO boundary: API responses expose product fields, never the persistence entity.
@Data
@AllArgsConstructor
public class ProductResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nama;
    private BigDecimal harga;
    private String kategori;
    private String brand;
    private LocalDateTime dateAdded;

    public static ProductResponse from(ProductEntity product) {
        return new ProductResponse(product.getId(), product.getNama(), product.getHarga(),
                product.getKategori(), product.getBrand(), product.getDateAdded());
    }
}
