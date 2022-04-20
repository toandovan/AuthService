package com.example.authservice.service;

import com.example.authservice.dto.CredentialsDto;
import com.example.authservice.dto.UserDto;

public interface UserService {
    UserDto signIn(CredentialsDto credentialsDto);
    UserDto getUserData(String token);
}
