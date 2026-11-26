package com.example.shop.Entity;

import com.example.shop.Enums.ProductStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter @Setter
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private String id;

    @Column(nullable = false, length = 200)
    private String name,decription;

    @Column(nullable = false,precision = 15,scale = 2)
    private BigDecimal price;

    private Integer stock;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",nullable = false)
    private Categories categories;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime update_at;

}
