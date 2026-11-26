package com.example.shop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum ErrorCode {
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,"khong tim thay danh muc"),
    CATEGORY_EXISTED(HttpStatus.CONFLICT,"Ten nay da ton tai"),
    CATEGORY_IN_USE(HttpStatus.CONFLICT,"Dang ton tai khoa ngoai"),
    DATE_CONFLICT(HttpStatus.CONFLICT,"loi du lieu");
    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    
}
