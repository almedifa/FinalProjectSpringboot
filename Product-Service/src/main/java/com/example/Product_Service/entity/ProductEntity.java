package com.example.Product_Service.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "mst_product")
@Data
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nama;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal harga;

    @Column(nullable = false, length = 100)
    private String kategori;

    @Column(nullable = false, length = 100)
    private String brand;

    // DummyJSON id is stored separately from our generated product id for import idempotency.
    @Column(name = "source_id", unique = true)
    private Long sourceId;

    // Nullable for compatibility with product rows created before this column existed.
    @Column(name = "date_added", updatable = false)
    private LocalDateTime dateAdded;

}
