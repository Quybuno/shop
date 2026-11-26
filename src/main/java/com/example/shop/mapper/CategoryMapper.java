package com.example.shop.mapper;

import com.example.shop.DTO.response.CategoryResponse;
import com.example.shop.Entity.Categories;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "decription",target = "description")
    @Mapping(source = "created_at", target = "createdAt")
    @Mapping(source = "update_at", target = "updatedAt")
    CategoryResponse toCategoryResponse(Categories categories);

}
