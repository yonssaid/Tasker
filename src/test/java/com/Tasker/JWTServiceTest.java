package com.Tasker;

import com.Tasker.Services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JWTServiceTest {

    @Autowired
    private JWTService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    public void setUp() {
        jwtService = new JWTService();
        userDetails = User.withUsername("testuser")
                .password("password")
                .authorities("ROLE_USER")
                .build();
    }

    @Test
    public void testGenerateToken() {
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    public void testExtractUsername() {
        String token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUsername(token);
        assertEquals("testuser", username);
    }

    @Test
    public void testIsTokenValid() {
        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.isTokenValid(token));
    }

    @Test
    public void testTokenExpiration() {
        String token = jwtService.generateToken(userDetails);
        Claims claims = Jwts.parser()
                .verifyWith(jwtService.generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Date expirationDate = claims.getExpiration();
        assertTrue(expirationDate.after(new Date()));
    }
}
