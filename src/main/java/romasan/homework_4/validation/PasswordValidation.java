package romasan.homework_4.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import romasan.homework_4.validation.validator.PasswordValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordValidation {
    String message() default "incorrect password format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
