package com.github.dishavarshney.Trimmy.service.impls;

import com.github.dishavarshney.trimmy.models.Users;
import com.github.dishavarshney.trimmy.repositories.UserRepository;
import com.github.dishavarshney.trimmy.service.impls.LoginServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class LoginServiceImplTest {

    private LoginServiceImpl loginService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        loginService = new LoginServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void testRegisterNewUser() {
        // Test case for new user registration
        String username = "testuser";
        String password = "testpassword";
        String id = "testId";

        when(userRepository.findByUsername(username.toLowerCase())).thenReturn(Optional.empty());

        boolean result = loginService.registerNewUser(username, password, id);

        assertTrue(result);
        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    void testRegisterNewUser_UserAlreadyExists() {
        // Test case for an existing user trying to register again
        String username = "existinguser";
        String password = "testpassword";
        String id = "testId";

        Users existingUser = new Users();
        existingUser.setUsername(username.toLowerCase());
        existingUser.setPassword("hashedpassword");

        when(userRepository.findByUsername(username.toLowerCase())).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.upgradeEncoding(anyString())).thenThrow(IllegalArgumentException.class);

        boolean result = loginService.registerNewUser(username, password, id);

        assertTrue(result);
        verify(userRepository, times(1)).save(any(Users.class));
    }

//    @Test
//    void testRegisterNewUser_PasswordEmpty() {
//        // Test case for registration with an empty password
//        String username = "testuser";
//        String password = "";
//        String id = "testId";
//
//        when(userRepository.findByUsername(username.toLowerCase())).thenReturn(Optional.empty());
//
//        boolean result = loginService.registerNewUser(username, password, id);
//
//        assertFalse(result);
//        verify(userRepository, never()).save(any(Users.class));
//    }

//    @Test
//    void testRegisterNewUser_IdEmpty() {
//        // Test case for registration with an empty Id
//        String username = "testuser";
//        String password = "testpassword";
//        String id = "";
//
//        when(userRepository.findByUsername(username.toLowerCase())).thenReturn(Optional.empty());
//
//        boolean result = loginService.registerNewUser(username, password, id);
//
//        assertFalse(result);
//        verify(userRepository, never()).save(any(Users.class));
//    }

    @Test
    void testLoadUserByUsername() {
        // Test case for loading user by username
        String username = "testuser";

        Users user = new Users();
        user.setUsername(username.toLowerCase());
        user.setPassword("hashedpassword");

        when(userRepository.findByUsername(username.toLowerCase())).thenReturn(Optional.of(user));

        assertEquals(user, loginService.loadUserByUsername(username));
    }

//    @Test
//    void testLoadUserByUsername_UserNotFound() {
//        // Test case when user not found
//        String username = "nonexistentuser";
//
//        when(userRepository.findByUsername(username.toLowerCase())).thenReturn(Optional.empty());
//
//        assertThrows(UsernameNotFoundException.class, () -> loginService.loadUserByUsername(username));
//    }
}
