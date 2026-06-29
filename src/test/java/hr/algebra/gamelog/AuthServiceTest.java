package hr.algebra.gamelog;

import hr.algebra.gamelog.dto.Dto;
import hr.algebra.gamelog.entity.User;
import hr.algebra.gamelog.enums.Role;
import hr.algebra.gamelog.repository.UserRepository;
import hr.algebra.gamelog.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void testRegisterWithExistingUsername() {
        when(userRepository.existsByUsername("admin")).thenReturn(true);

        Dto.RegisterRequest request = new Dto.RegisterRequest("admin", "admin@test.com", "password123");

        assertThrows(IllegalArgumentException.class, () -> authService.register(request));
    }

    @Test
    void testRegisterWithExistingEmail() {
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("existing@test.com")).thenReturn(true);

        Dto.RegisterRequest request = new Dto.RegisterRequest("newuser", "existing@test.com", "password123");

        assertThrows(IllegalArgumentException.class, () -> authService.register(request));
    }

    @Test
    void testRegisterSuccess() {
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("new@test.com")).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("hashedpassword");

        User savedUser = new User();
        savedUser.setUsername("newuser");
        savedUser.setRole(Role.USER);
        when(userRepository.save(any())).thenReturn(savedUser);

        Dto.RegisterRequest request = new Dto.RegisterRequest("newuser", "new@test.com", "password123");

        assertDoesNotThrow(() -> authService.register(request));
        verify(userRepository, times(1)).save(any());
    }
}