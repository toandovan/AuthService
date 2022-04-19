package com.example.authservice.service;

import com.example.authservice.dto.CredentialsDto;

public interface UserService {
    String signIn(CredentialsDto credentialsDto);
}
