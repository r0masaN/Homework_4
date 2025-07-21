package romasan.homework_4.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import romasan.homework_4.validation.validator.LoginValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = LoginValidator.class)
public @interface LoginValidation {
    String message() default "incorrect login format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
