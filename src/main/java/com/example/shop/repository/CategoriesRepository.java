package com.example.shop.repository;

import com.example.shop.Entity.Categories;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories,String> {
    boolean existsByNameIgnoreCase(@NotBlank(message = "Ten khong duoc de trong") @Size(min = 2, max = 100, message = "kich thuoc khong hop le") String name);

    // dung khi update
    boolean existsByNameIgnoreCaseAndIdNot(String name, String category_id);

}
