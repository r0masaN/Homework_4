package romasan.homework_4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import romasan.homework_4.model.UserDTO;
import romasan.homework_4.service.AuthorizationService;

@RestController
@RequestMapping("/api/v1/authorization")
public class AuthorizationController {
    private final AuthorizationService service;

    @Autowired
    public AuthorizationController(final AuthorizationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> authorize(@Validated @RequestBody final UserDTO user) {
        return this.service.authorize(user);
    }

    @PostMapping("/exit")
    public ResponseEntity<String> exit(@RequestParam final String token) {
        return this.service.exit(token);
    }
}
