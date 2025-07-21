package romasan.homework_4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import romasan.homework_4.model.User;
import romasan.homework_4.model.UserDTO;
import romasan.homework_4.repository.UserRepository;
import romasan.homework_4.service.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationServiceImpl(final UserRepository repository, final PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<String> register(final UserDTO newUser) {
        if (this.repository.findByLogin(newUser.getLogin()).isPresent() || this.repository.findByEmail(newUser.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("");
        }

        final User user = new User();
        user.setLogin(newUser.getLogin());
        user.setEmail(newUser.getEmail());
        user.setRoles(newUser.getRoles());
        user.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
        this.repository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
