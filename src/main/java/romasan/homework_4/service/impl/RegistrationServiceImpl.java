package romasan.homework_4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import romasan.homework_4.model.User;
import romasan.homework_4.model.DTO.UserRegistrationDTO;
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
    public ResponseEntity<String> register(final UserRegistrationDTO newUserDTO) {
        if (this.repository.findByLogin(newUserDTO.getLogin()).isPresent() || this.repository.findByEmail(newUserDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("");
        }

        final User newUser = new User();
        newUser.setLogin(newUserDTO.getLogin());
        newUser.setEmail(newUserDTO.getEmail());
        newUser.setRoles(newUserDTO.getRoles());
        newUser.setPassword(this.passwordEncoder.encode(newUserDTO.getPassword()));
        this.repository.save(newUser);
        return ResponseEntity.ok("User registered successfully");
    }
}
