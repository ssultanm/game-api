package org.example.gameapi.validation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationExceptionHandler {

    // @Valid @RequestBody -> MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleBody(MethodArgumentNotValidException ex,
                                              HttpServletRequest request) {

        List<Violation> violations = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                violations.add(new Violation(
                        error.getField(),
                        error.getRejectedValue(),
                        error.getDefaultMessage())));

        ex.getBindingResult().getGlobalErrors().forEach(error ->
                violations.add(new Violation(
                        "object",
                        null,
                        error.getDefaultMessage())));

        return build(violations, request);
    }

    // @Validated на классе + @Positive на параметре -> ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleParams(ConstraintViolationException ex,
                                                HttpServletRequest request) {

        List<Violation> violations = ex.getConstraintViolations().stream()
                .map(v -> new Violation(
                        lastNode(v.getPropertyPath()),
                        v.getInvalidValue(),
                        v.getMessage()))
                .toList();

        return build(violations, request);
    }

    private ValidationErrorResponse build(List<Violation> violations, HttpServletRequest request) {
        return new ValidationErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                "Запрос содержит " + violations.size() + " ошибк(и) валидации",
                request.getRequestURI(),
                violations);
    }

    private String lastNode(Path path) {
        String full = path.toString();
        return full.substring(full.lastIndexOf('.') + 1);
    }

    public record ValidationErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            String message,
            String path,
            List<Violation> violations
    ) {}

    public record Violation(String field, Object rejectedValue, String message) {}
}