package romasan.homework_4.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import romasan.homework_4.validation.LoginValidation;

public class LoginValidator implements ConstraintValidator<LoginValidation, String> {
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return value.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-_!@]).{8,24}$");
    }
}
