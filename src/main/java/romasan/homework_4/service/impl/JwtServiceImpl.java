package romasan.homework_4.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import romasan.homework_4.configuration.JwtProperties;
import romasan.homework_4.model.User;
import romasan.homework_4.service.JwtService;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtServiceImpl implements JwtService {
    private final JwtProperties properties;

    public JwtServiceImpl(final JwtProperties properties) {
        this.properties = properties;
    }

    @Override
    public String generateToken(final User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("login", user.getLogin())
                .claim("roles", user.getRoles())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 4 * 60 * 60 * 1000)) // 4 hours
                .signWith(Keys.hmacShaKeyFor(this.properties.getSecretKey().getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    @Override
    public UUID validateTokenAndGetUserId(final String token) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.properties.getSecretKey().getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return UUID.fromString(claims.getSubject());
    }
}
