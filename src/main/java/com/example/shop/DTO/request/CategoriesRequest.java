package com.example.shop.DTO.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoriesRequest {
    @Valid
    @NotBlank(message = "Ten khong duoc de trong")
    @Size(min = 2,max = 100, message = "kich thuoc khong hop le")
    String name;


    @Size(max = 500)
    String description;
}
