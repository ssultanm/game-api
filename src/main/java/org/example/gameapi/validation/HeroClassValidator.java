package org.example.gameapi.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class HeroClassValidator implements ConstraintValidator<HeroClass, String> {

    private static final Set<String> ALLOWED = Set.of("WARRIOR", "MAGE", "ARCHER");

    private boolean ignoreCase;

    @Override
    public void initialize(HeroClass annotation) {
        this.ignoreCase = annotation.ignoreCase();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // пустоту проверяет @NotBlank, не мы
        }

        String normalized = ignoreCase ? value.toUpperCase() : value;
        if (ALLOWED.contains(normalized)) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                        "Класс '" + value + "' не существует. Доступны: " + String.join(", ", ALLOWED))
                .addConstraintViolation();
        return false;
    }
}