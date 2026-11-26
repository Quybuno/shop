package com.example.shop.controller;

import com.example.shop.DTO.request.CategoriesRequest;
import com.example.shop.DTO.response.CategoryResponse;
import com.example.shop.DTO.response.PageResponse;
import com.example.shop.Entity.Categories;
import com.example.shop.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.shop.DTO.response.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public PageResponse<CategoryResponse> getCategories(
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return categoryService.getCategories(pageable);
    }

    @GetMapping("/{id}")
    public CategoryResponse getById(@PathVariable String id){
        return categoryService.getCategoriesById(id);
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable String id, @RequestBody @Valid CategoriesRequest request){
        return categoryService.updateCategories(id,request);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@RequestBody @Valid CategoriesRequest request){
        return categoryService.createCategories(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id){
        categoryService.delete(id);
    }

}
