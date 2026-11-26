package com.example.shop.DTO.response;


import com.example.shop.Entity.Categories;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse
{
    String id,name, description;
    LocalDateTime createdAt, updatedAt;

    public static CategoryResponse from(Categories c){
        return new CategoryResponse(c.getId(),c.getName(),c.getDecription(),c.getCreated_at(),c.getUpdate_at());
    }


}
