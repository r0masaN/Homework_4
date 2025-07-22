package romasan.homework_4.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
    public String generateAccessToken(final User user) {
        return this.buildToken(user, 4 * 60 * 60 * 1000); // 4 hours
    }

    @Override
    public String generateRefreshToken(final User user) {
        return this.buildToken(user, 7 * 24 * 60 * 60 * 1000); // 7 days
    }

    @Override
    public UUID validateTokenAndGetUserId(final String token) {
        final Claims claims = getClaims(token);
        return UUID.fromString(claims.getSubject());
    }

    @Override
    public boolean validateToken(final String token) {
        try {
            this.getClaims(token);
            return true;
        } catch (final JwtException | IllegalArgumentException e) {
            System.out.println("TOKEN VALIDATION ERROR: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String extractLogin(final String token) {
        return this.getClaims(token).get("login", String.class);
    }

    private String buildToken(final User user, final long validityInMillis) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("login", user.getLogin())
                .claim("roles", user.getRoles())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validityInMillis))
                .signWith(
                        Keys.hmacShaKeyFor(this.properties.getSecretKey().getBytes(StandardCharsets.UTF_8)),
                        SignatureAlgorithm.HS384
                )
                .compact();
    }

    private Claims getClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.properties.getSecretKey().getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
