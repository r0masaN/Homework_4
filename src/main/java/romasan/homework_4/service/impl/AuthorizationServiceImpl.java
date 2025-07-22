package romasan.homework_4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import romasan.homework_4.exception.UserNotFoundException;
import romasan.homework_4.model.DTO.UserAuthorizationDTO;
import romasan.homework_4.model.User;
import romasan.homework_4.repository.UserRepository;
import romasan.homework_4.service.AuthorizationService;
import romasan.homework_4.service.JwtService;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizationServiceImpl(final UserRepository repository, final JwtService jwtService, final PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Map<String, String> authorize(final UserAuthorizationDTO user) {
        final Optional<User> existingUser = this.repository.findByLogin(user.getLogin());

        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("User with login \"%s\" not found!".formatted(user.getLogin()));
        }

        final User foundUser = existingUser.get();

        if (!this.passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            throw new IllegalArgumentException("Incorrect password!");
        }

        final String accessToken = this.jwtService.generateAccessToken(foundUser);
        final String refreshToken = this.jwtService.generateRefreshToken(foundUser);

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    @Override
    public Optional<User> findByLogin(final String login) {
        return this.repository.findByLogin(login);
    }
}
