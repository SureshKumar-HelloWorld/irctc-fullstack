package com.irctc.onlinetrainticketbooking.service;

import com.irctc.onlinetrainticketbooking.dto.AuthRequest;
import com.irctc.onlinetrainticketbooking.dto.AuthResponse;

public interface UserService {
    AuthResponse register(AuthRequest request, boolean asAdmin);
    AuthResponse login(String email, String rawPassword);
}
