package com.irctc.onlinetrainticketbooking.service;

import com.irctc.onlinetrainticketbooking.config.JwtService;
import com.irctc.onlinetrainticketbooking.dto.AuthRequest;
import com.irctc.onlinetrainticketbooking.dto.AuthResponse;
import com.irctc.onlinetrainticketbooking.entity.Role;
import com.irctc.onlinetrainticketbooking.entity.User;
import com.irctc.onlinetrainticketbooking.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder, AuthenticationManager authManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse register(AuthRequest request, boolean asAdmin) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        if (asAdmin) roles.add(Role.ADMIN);

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        String token = jwtService.generateToken(user.getUsername(), claims);
        return new AuthResponse(token, user.getEmail(), roles);
    }

    @Override
    public AuthResponse login(String email, String rawPassword) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(email, rawPassword));
        User user = userRepository.findByEmail(email).orElseThrow();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles());
        String token = jwtService.generateToken(email, claims);
        return new AuthResponse(token, email, user.getRoles());
    }
}
