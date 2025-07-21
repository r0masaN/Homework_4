package romasan.homework_4.service;

import org.springframework.http.ResponseEntity;
import romasan.homework_4.model.User;

import java.util.UUID;

public interface InfoService {
    ResponseEntity<User> getInfo(UUID id);
}
