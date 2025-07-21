package romasan.homework_4.service;

import org.springframework.http.ResponseEntity;
import romasan.homework_4.model.UserDTO;

public interface AuthorizationService {
    ResponseEntity<String> authorize(UserDTO user);

    ResponseEntity<String> exit(String token);
}
