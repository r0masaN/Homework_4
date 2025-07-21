package romasan.homework_4.service;

import romasan.homework_4.model.User;

import java.util.UUID;

public interface JwtService {
    String generateToken(User user);

    UUID validateTokenAndGetUserId(String token);
}
