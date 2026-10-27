package org.example.gameapi.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    Long id;
    String name;
    String email;
    String username;
    String password;
    String role;
}
