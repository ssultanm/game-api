package org.example.gameapi.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConsistentItemValidator.class)
@Documented
public @interface ConsistentItem {

    String message() default "Сила предмета не соответствует его типу";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

