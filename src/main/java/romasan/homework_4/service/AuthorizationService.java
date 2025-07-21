package romasan.homework_4.service;

import org.springframework.http.ResponseEntity;
import romasan.homework_4.model.DTO.UserAuthorizationDTO;

public interface AuthorizationService {
    ResponseEntity<String> authorize(UserAuthorizationDTO user);

    ResponseEntity<String> exit(String token);
}
