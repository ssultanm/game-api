package org.example.gameapi.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.gameapi.entity.Item;

import java.util.Set;

public class ConsistentItemValidator implements ConstraintValidator<ConsistentItem, Item> {

    private static final Set<String> ALLOWED = Set.of("WEAPON", "ARMOR", "POTION");

    @Override
    public boolean isValid(Item item, ConstraintValidatorContext context) {
        if (item == null || item.getType() == null || item.getType().isBlank()) {
            return true;
        }

        String type = item.getType().toUpperCase();

        if (!ALLOWED.contains(type)) {
            return violation(context, "type",
                    "Тип '" + item.getType() + "' не существует. Доступны: " + String.join(", ", ALLOWED));
        }

        boolean ok = switch (type) {
            case "WEAPON", "ARMOR" -> item.getPower() > 0;
            case "POTION" -> item.getPower() == 0;
            default -> true;
        };

        if (ok) {
            return true;
        }

        String message = "POTION".equals(type)
                ? "У зелья power должен быть 0"
                : "У " + type + " power должен быть больше 0";

        return violation(context, "power", message);
    }

    private boolean violation(ConstraintValidatorContext context, String field, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(field)   // без этого фронт не поймёт, какое поле подсветить
                .addConstraintViolation();
        return false;
    }
}
