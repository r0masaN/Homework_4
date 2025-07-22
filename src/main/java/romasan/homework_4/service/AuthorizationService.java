package romasan.homework_4.service;

import romasan.homework_4.model.DTO.UserAuthorizationDTO;
import romasan.homework_4.model.User;

import java.util.Map;
import java.util.Optional;

public interface AuthorizationService {
    Map<String, String> authorize(UserAuthorizationDTO user);

    Optional<User> findByLogin(String login);
}
