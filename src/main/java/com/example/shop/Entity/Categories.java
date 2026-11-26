package com.example.shop.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "categories")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    private String id;

    @Column(nullable = false,unique = true,length = 100)
    private String name;
    private String decription;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp        
    private LocalDateTime update_at;

}
