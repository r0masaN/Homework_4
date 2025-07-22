package romasan.homework_4.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import romasan.homework_4.model.User;
import romasan.homework_4.service.BlackListService;
import romasan.homework_4.service.InfoService;

@RestController
@RequestMapping("/api/v1/info")
public class InfoController {
    private final InfoService infoService;
    private final BlackListService blackListService;

    @Autowired
    public InfoController(final InfoService infoService, final BlackListService blackListService)  {
        this.infoService = infoService;
        this.blackListService = blackListService;
    }

    @GetMapping
    public ResponseEntity<User> getInfo(final Authentication authentication, final HttpServletRequest request) {
        if (this.blackListService.containsToken(request.getHeader("Authorization").substring("Bearer ".length())))  {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final User user = (User) authentication.getPrincipal();
        return this.infoService.getInfo(user.getId());
    }
}
