package com.example.authservice.service;

import com.example.authservice.dto.CredentialsDto;
import com.example.authservice.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> signIn(CredentialsDto credentialsDto);
}
