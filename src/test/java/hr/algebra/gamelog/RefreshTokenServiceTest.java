package hr.algebra.gamelog;

import hr.algebra.gamelog.entity.RefreshToken;
import hr.algebra.gamelog.entity.User;
import hr.algebra.gamelog.enums.Role;
import hr.algebra.gamelog.repository.RefreshTokenRepository;
import hr.algebra.gamelog.service.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    private User testUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setRole(Role.USER);
        return user;
    }

    @Test
    void testCreateRefreshToken() {
        ReflectionTestUtils.setField(refreshTokenService, "refreshExpiryMs", 604800000L);
        User user = testUser();

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken("test-uuid");
        token.setExpiryDate(Instant.now().plusMillis(604800000L));
        token.setRevoked(false);

        when(refreshTokenRepository.save(any())).thenReturn(token);
        doNothing().when(refreshTokenRepository).deleteByUser(user);

        RefreshToken result = refreshTokenService.createRefreshToken(user);
        assertNotNull(result);
        assertFalse(result.isRevoked());
    }

    @Test
    void testFindByToken() {
        RefreshToken token = new RefreshToken();
        token.setToken("test-uuid");
        when(refreshTokenRepository.findByToken("test-uuid")).thenReturn(Optional.of(token));

        Optional<RefreshToken> result = refreshTokenService.findByToken("test-uuid");
        assertTrue(result.isPresent());
    }

    @Test
    void testIsValidReturnsTrueForValidToken() {
        RefreshToken token = new RefreshToken();
        token.setRevoked(false);
        token.setExpiryDate(Instant.now().plusSeconds(3600));
        assertTrue(refreshTokenService.isValid(token));
    }

    @Test
    void testIsValidReturnsFalseForRevokedToken() {
        RefreshToken token = new RefreshToken();
        token.setRevoked(true);
        token.setExpiryDate(Instant.now().plusSeconds(3600));
        assertFalse(refreshTokenService.isValid(token));
    }

    @Test
    void testRevokeByUser() {
        User user = testUser();
        doNothing().when(refreshTokenRepository).deleteByUser(user);
        refreshTokenService.revokeByUser(user);
        verify(refreshTokenRepository, times(1)).deleteByUser(user);
    }
}