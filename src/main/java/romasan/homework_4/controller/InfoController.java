package romasan.homework_4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import romasan.homework_4.model.User;
import romasan.homework_4.service.InfoService;

@RestController
@RequestMapping("/api/v1/info")
public class InfoController {
    private final InfoService service;

    @Autowired
    public InfoController(final InfoService service)  {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<User> getInfo(final Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        return this.service.getInfo(user.getId());
    }
}
