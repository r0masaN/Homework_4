package romasan.homework_4.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import romasan.homework_4.validation.PasswordValidation;

public class PasswordValidator implements ConstraintValidator<PasswordValidation, String> {
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return value.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-_!@]).{10,32}$");
    }
}
