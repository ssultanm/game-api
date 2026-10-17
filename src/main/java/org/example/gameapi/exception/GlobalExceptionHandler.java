package org.example.gameapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({HeroNotFoundException.class, })
    public ResponseEntity<ApiError> handleNotFound(
            RuntimeException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> handleNoResource(
            NoResourceFoundException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, "Адрес не найден", request);
    }

    // 500 — всё остальное: ошибка в нашем коде
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(
            Exception ex, HttpServletRequest request) {
        ex.printStackTrace(); // подробности — в консоль
        return build(HttpStatus.INTERNAL_SERVER_ERROR,
                "Внутренняя ошибка сервера", request);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Ошибка валидации",
                request.getRequestURI());
        error.setErrors(errors); // build() не подходит: нужен errors
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // один шаблон ответа для всех методов выше
    private ResponseEntity<ApiError> build(HttpStatus status, String message,
                                           HttpServletRequest request) {
        ApiError error = new ApiError(status, message, request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}

