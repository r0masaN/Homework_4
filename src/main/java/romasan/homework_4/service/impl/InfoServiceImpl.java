package romasan.homework_4.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import romasan.homework_4.exception.UserNotFoundException;
import romasan.homework_4.model.User;
import romasan.homework_4.repository.UserRepository;
import romasan.homework_4.service.InfoService;

import java.util.UUID;

@Service
public class InfoServiceImpl implements InfoService {
    private final UserRepository repository;

    @Autowired
    public InfoServiceImpl(final UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<User> getInfo(final UUID id) {
        return ResponseEntity.ok(this.repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("No user found with id %s", id))));
    }
}
