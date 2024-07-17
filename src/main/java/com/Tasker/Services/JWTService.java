package com.Tasker.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
public class JWTService {

    private static final Logger logger = Logger.getLogger(JWTService.class.getName());
    private static final String secretkey = "17ED437C3814863F6A77BF729797F55784E8A25119CBF9E4CDECBD3984FFF07C";
    private static final long validTimeLength = TimeUnit.DAYS.toMillis(1);

    public SecretKey generateKey() {
        logger.info("Generating secret key...");

        byte[] decodedKey = Base64.getDecoder().decode(secretkey);
        SecretKey key = Keys.hmacShaKeyFor(decodedKey);

        logger.info("Secret key generated.");

        return key;
    }

    public String generateToken(UserDetails userDetails) {
        logger.info("Generating JWT token for user: " + userDetails.getUsername());

        Map<String, String> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());

        String token = Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(validTimeLength)))
                .signWith(generateKey())
                .compact();

        logger.info("JWT token generated successfully." );

        return token;
    }

    public String extractUsername(String jwt) {
        if (jwt == null || jwt.isEmpty()) {
            logger.warning("JWT is null or empty. Cannot extract username.");
            return null;
        }

        logger.info("Extracting username from JWT. JWT: " + jwt);

        Claims claims = getClaims(jwt);
        if (claims == null) {
            logger.warning("Failed to parse claims from JWT.");
            return null;
        }

        String username = claims.getSubject();

        logger.info("Username extracted: " + username);

        return username;
    }


    private Claims getClaims(String jwt) {
        logger.info("Parsing JWT and retrieving claims. JWT: " + jwt);

        Claims claims = Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();

        logger.info("JWT parsed successfully.");

        return claims;
    }

    public boolean isTokenValid(String jwt) {
        logger.info("Validating JWT token. JWT: " + jwt);

        Claims claims = getClaims(jwt);
        boolean isValid = claims.getExpiration().after(Date.from(Instant.now()));

        logger.info("JWT token validity: " + isValid);

        return isValid;
    }
}
