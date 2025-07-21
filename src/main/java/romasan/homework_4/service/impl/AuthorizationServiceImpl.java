package romasan.homework_4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import romasan.homework_4.model.UserDTO;
import romasan.homework_4.model.User;
import romasan.homework_4.repository.UserRepository;
import romasan.homework_4.service.AuthorizationService;
import romasan.homework_4.service.JwtService;

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
    public ResponseEntity<String> authorize(final UserDTO user) {
        final Optional<User> existingUser = this.repository.findByLogin(user.getLogin());
        if (existingUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(String.format("User with login \"%s\" not found!", user.getLogin()));
        }

        if (!this.passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password!");
        }

        final String token = this.jwtService.generateToken(existingUser.get());
        return ResponseEntity.ok(token);
    }

    @Override
    public ResponseEntity<String> exit(final String token) {
        //TODO exit
        return ResponseEntity.ok("User exited successfully!");
    }
}
