package romasan.homework_4.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import romasan.homework_4.configuration.JwtProperties;
import romasan.homework_4.model.User;
import romasan.homework_4.service.JwtService;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

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
        return String.class.cast(this.decryptClaims(this.getClaims(token).get("enc", String.class)).get("login"));
    }

    private String buildToken(final User user, final long validityInMillis) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("login", user.getLogin());
        claims.put("roles", user.getRoles());
        final String encryptedClaims = this.encryptClaims(claims);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("enc", encryptedClaims)
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

    private String encryptClaims(final Map<String, Object> claims) {
        try {
            final SecretKey secretKey = new SecretKeySpec(this.properties.getSecretKey().getBytes(StandardCharsets.UTF_8), "AES");
            final Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            final String json = new ObjectMapper().writeValueAsString(claims);
            final byte[] encryptedBytes = cipher.doFinal(json.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encryptedBytes);

        } catch (final NoSuchPaddingException
                       | NoSuchAlgorithmException
                       | InvalidKeyException
                       | JsonProcessingException
                       | IllegalBlockSizeException
                       | BadPaddingException e) {
            throw new RuntimeException("Encryption failed!", e);

        } finally {

        }
    }

    private Map<String, Object> decryptClaims(final String encryptedStr) {
        try {
            final SecretKey secretKey = new SecretKeySpec(this.properties.getSecretKey().getBytes(StandardCharsets.UTF_8), "AES");
            final Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            final byte[] decodedBytes = Base64.getDecoder().decode(encryptedStr);
            final byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            final String json = new String(decryptedBytes, StandardCharsets.UTF_8);

            return new ObjectMapper().readValue(json, new TypeReference<>() {});

        } catch (NoSuchPaddingException
                 | IllegalBlockSizeException
                 | NoSuchAlgorithmException
                 | BadPaddingException
                 | InvalidKeyException
                 | JsonProcessingException e) {
            throw new RuntimeException("Encryption failed!", e);

        } finally {

        }
    }
}
