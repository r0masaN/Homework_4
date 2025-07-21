package romasan.homework_4.service;

import org.springframework.http.ResponseEntity;
import romasan.homework_4.model.DTO.UserRegistrationDTO;

public interface RegistrationService {
    ResponseEntity<String> register(UserRegistrationDTO newUserDTO);
}
