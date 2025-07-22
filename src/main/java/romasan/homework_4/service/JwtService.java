package romasan.homework_4.service;

import romasan.homework_4.model.User;

import java.util.UUID;

public interface JwtService {
    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    UUID validateTokenAndGetUserId(String token);

    boolean validateToken(String token);

    String extractLogin(String token);
}
