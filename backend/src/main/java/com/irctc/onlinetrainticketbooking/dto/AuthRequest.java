package com.irctc.onlinetrainticketbooking.dto;
import lombok.Data;
@Data
public class AuthRequest {
    private String name;
    private String email;
    private String password;
}
