package com.example.shop.exception;

import com.example.shop.DTO.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ErrorResponse> handleApp(AppException exception, HttpServletRequest httpServletRequest){
        ErrorCode errorCode = exception.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(errorCode.getStatus().value())
                .error(errorCode.getStatus().getReasonPhrase()) 
                .message((errorCode.getMessage()))
                .path(httpServletRequest.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        ErrorResponse body = ErrorResponse.builder()
                .status(400)
                .error("Bad Request")
                .message(message)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.badRequest().body(body);
    }
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException e,HttpServletRequest httpServletRequest){
        ErrorCode errorCode = ErrorCode.DATA_CONFLICT;

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status((errorCode.getStatus().value()))
                .error(errorCode.getStatus().getReasonPhrase())
                .message(errorCode.getMessage())
                .path(httpServletRequest.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

}
