package com.example.shop.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.query.Order;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter @Setter
@NoArgsConstructor
public class Order_items {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_items_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produc_id",nullable = false)
    private Products products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",nullable = false)
    private Orders orders;

//    @DecimalMin(value = )
    @Min(value = 1, message = "so luong phai lon hon 1")
    private int quantity;
    
    private BigDecimal unitPrice;

}
