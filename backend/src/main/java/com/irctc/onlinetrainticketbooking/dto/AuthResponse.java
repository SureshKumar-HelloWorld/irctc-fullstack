package com.irctc.onlinetrainticketbooking.dto;

import com.irctc.onlinetrainticketbooking.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private Set<Role> roles;
}
