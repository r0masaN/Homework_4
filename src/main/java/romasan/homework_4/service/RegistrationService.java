package romasan.homework_4.service;

import org.springframework.http.ResponseEntity;
import romasan.homework_4.model.User;
import romasan.homework_4.model.UserDTO;

public interface RegistrationService {
    ResponseEntity<String> register(UserDTO newUser);
}
