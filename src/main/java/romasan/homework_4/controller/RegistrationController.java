package romasan.homework_4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import romasan.homework_4.model.User;
import romasan.homework_4.model.UserDTO;
import romasan.homework_4.service.RegistrationService;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {
    private final RegistrationService service;

    @Autowired
    public RegistrationController(final RegistrationService service)  {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> register(@Validated @RequestBody final UserDTO newUser) {
        return this.service.register(newUser);
    }
}
