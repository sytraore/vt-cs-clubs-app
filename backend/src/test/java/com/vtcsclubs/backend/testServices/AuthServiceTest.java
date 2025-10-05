package com.vtcsclubs.backend.testServices;
import com.vtcsclubs.backend.dto.RegisterRequest;
import com.vtcsclubs.backend.models.AdminUser;
import com.vtcsclubs.backend.models.Club;
import com.vtcsclubs.backend.repositories.AdminUserRepository;
import com.vtcsclubs.backend.services.AuthService;
import com.vtcsclubs.backend.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    // fake mocked dependencies
    @Mock
    private AdminUserRepository adminUserRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;

    // inject the mocks into our AuthService instance
    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;
    private AdminUser savedUser;
    //private Club mockClub;

    @BeforeEach
    void setUp() {
        // Set up common test data before each test
        //mockClub = new Club("BITS", "Develop projects", "abc@vt.edu");
        registerRequest = new RegisterRequest("testuser", "password123");
        savedUser = new AdminUser("testuser", "hashedPassword");
    }

    @Test
    void register_ShouldSaveUserAndReturnToken() {
        // 1. When passwordEncoder.encode is called with "password123", return "hashedPassword"
        when(passwordEncoder.encode(registerRequest.password())).thenReturn("hashedPassword");

        // 2. When adminUserRepository.save is called with any AdminUser object, return our savedUser
        when(adminUserRepository.save(any(AdminUser.class))).thenReturn(savedUser);

        // 3. When jwtService.generateToken is called with our savedUser, return a fake token
        //when(jwtService.generateToken(any(AdminUser.class))).thenReturn("fake.jwt.token");
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("fake.jwt.token");
        // Call the method we are testing
        var authResponse = authService.register(registerRequest);

        // 4. Check that the response is not null and the token is what we expect
        assertNotNull(authResponse);
        assertNotNull(authResponse.token());

        // 5. Verify that the adminUserRepository's save method was called exactly once
        verify(adminUserRepository).save(any(AdminUser.class));
    }
}
