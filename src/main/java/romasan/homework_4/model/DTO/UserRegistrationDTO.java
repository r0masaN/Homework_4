package romasan.homework_4.model.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import romasan.homework_4.model.UserRole;
import romasan.homework_4.validation.LoginValidation;
import romasan.homework_4.validation.PasswordValidation;

import java.util.HashSet;
import java.util.Set;

public final class UserRegistrationDTO {
    @LoginValidation(message = "login (8-24 symbols) must contain at least 1 lower, upper, numeric and special symbol")
    private String login;
    @PasswordValidation(message = "password (10-32 symbols) must contain at least 1 lower, upper, numeric and special symbol")
    private String password;
    @NotBlank(message = "email can't be blank")
    @Email(message = "incorrect e-mail format")
    private String email;
    private Set<UserRole> roles = new HashSet<>();

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public Set<UserRole> getRoles() {
        return this.roles;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setRoles(final Set<UserRole> roles) {
        this.roles = roles;
    }
}
