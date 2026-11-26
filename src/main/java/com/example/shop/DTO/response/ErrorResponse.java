package com.example.shop.DTO.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
        private int status;          // 404, 409, 400...
        private String error;        // "Not Found", "Conflict"...
        private String message;      // nội dung cho người dùng đọc
        private String path;         // /api/categories
        private LocalDateTime timestamp;
        private Map<String, String> details;  // lỗi từng field (chỉ dùng cho validation)

}
