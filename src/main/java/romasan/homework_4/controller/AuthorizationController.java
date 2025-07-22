package romasan.homework_4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import romasan.homework_4.model.DTO.UserAuthorizationDTO;
import romasan.homework_4.model.User;
import romasan.homework_4.service.AuthorizationService;
import romasan.homework_4.service.BlackListService;
import romasan.homework_4.service.JwtService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/authorization")
public class AuthorizationController {
    private final AuthorizationService authorizationService;
    private final JwtService jwtService;
    private final BlackListService blackListService;

    @Autowired
    public AuthorizationController(final AuthorizationService authorizationService, final JwtService jwtService, final BlackListService blackListService) {
        this.authorizationService = authorizationService;
        this.jwtService = jwtService;
        this.blackListService = blackListService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> authorize(@Validated @RequestBody final UserAuthorizationDTO userDTO) {
        return ResponseEntity.ok(this.authorizationService.authorize(userDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestParam String refreshToken) {
        if (!this.jwtService.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid refresh token!"));
        }

        final String login = this.jwtService.extractLogin(refreshToken);
        final Optional<User> user = this.authorizationService.findByLogin(login);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "User not found!"));
        }

        final String newAccessToken = this.jwtService.generateAccessToken(user.get());

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }

    @PostMapping("/exit")
    public ResponseEntity<String> exit(@RequestHeader("Authorization") final String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Missing Authorization header!");
        }
        final String token = authHeader.substring("Bearer ".length());

        final boolean status = this.blackListService.addToken(token);
        return status
                ? ResponseEntity.ok("Token revoked!")
                : ResponseEntity.badRequest().body("Token is already withdrawn!");
    }
}
