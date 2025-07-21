package romasan.homework_4.model.DTO;

import romasan.homework_4.validation.LoginValidation;
import romasan.homework_4.validation.PasswordValidation;

public class UserAuthorizationDTO {
    @LoginValidation(message = "login (8-24 symbols) must contain at least 1 lower, upper, numeric and special symbol")
    private String login;
    @PasswordValidation(message = "password (10-32 symbols) must contain at least 1 lower, upper, numeric and special symbol")
    private String password;

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
