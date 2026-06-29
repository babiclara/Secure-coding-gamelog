package hr.algebra.gamelog;

import hr.algebra.gamelog.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", "TestSecretKey2026ChangeThisInProductionToSomethingMuchLonger");
        ReflectionTestUtils.setField(jwtService, "accessExpiryMs", 900000L);
    }

    @Test
    void testGenerateTokenNotNull() {
        UserDetails user = new User("testuser", "password", Collections.emptyList());
        String token = jwtService.generateAccessToken(user);
        assertNotNull(token);
    }

    @Test
    void testExtractUsername() {
        UserDetails user = new User("testuser", "password", Collections.emptyList());
        String token = jwtService.generateAccessToken(user);
        String username = jwtService.extractUsername(token);
        assertEquals("testuser", username);
    }

    @Test
    void testTokenIsValid() {
        UserDetails user = new User("testuser", "password", Collections.emptyList());
        String token = jwtService.generateAccessToken(user);
        assertTrue(jwtService.isValid(token, user));
    }

    @Test
    void testInvalidTokenReturnsFalse() {
        UserDetails user = new User("testuser", "password", Collections.emptyList());
        assertFalse(jwtService.isValid("invalidtoken", user));
    }

    @Test
    void testExtractUsernameFromInvalidToken() {
        assertNull(jwtService.extractUsername("invalidtoken"));
    }
}