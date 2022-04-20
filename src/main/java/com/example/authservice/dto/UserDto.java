package com.example.authservice.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {
    public UserDto(long id, String username, String token, String role) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.role=role;
    }

    private long id;
    private String username;
    private String token;
    private String role;
}
