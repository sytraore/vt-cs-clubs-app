package com.vtcsclubs.backend.services;
import com.vtcsclubs.backend.dto.AuthResponse;
import com.vtcsclubs.backend.dto.LoginRequest;
import com.vtcsclubs.backend.dto.RegisterRequest;
import com.vtcsclubs.backend.models.AdminUser;
import com.vtcsclubs.backend.repositories.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService {
    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AuthService(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        // Create a new user and save it to the database
        AdminUser adminUser = new AdminUser();
        adminUser.setUserEmail(request.email());
        // Hash the password before saving
        adminUser.setPassword(passwordEncoder.encode(request.password()));
        adminUserRepository.save(adminUser);

        // Generate a JWT for the new user
        UserDetails userDetails = new User(adminUser.getUsername(), adminUser.getPassword(), new ArrayList<>());
        String jwtToken = jwtService.generateToken(userDetails);

        return new AuthResponse(jwtToken);
    }

    public AuthResponse login(LoginRequest request) {
        // Find the user in the database
        AdminUser adminUser = adminUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        // Check if the provided password matches the stored hashed password
        if (!passwordEncoder.matches(request.password(), adminUser.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Generate a JWT for the user
        UserDetails userDetails = new User(adminUser.getUsername(), adminUser.getPassword(), new ArrayList<>());
        String jwtToken = jwtService.generateToken(userDetails);

        return new AuthResponse(jwtToken);
    }
}
