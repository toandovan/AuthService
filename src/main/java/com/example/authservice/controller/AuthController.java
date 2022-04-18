package com.example.authservice.controller;

import com.example.authservice.dto.CredentialsDto;
import com.example.authservice.entity.User;
import com.example.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signIn")
    public ResponseEntity<Optional<User>> signIn(@RequestBody CredentialsDto credentialsDto) {
        return ResponseEntity.ok(userService.signIn(credentialsDto));
    }

    @GetMapping("/test")
    public String signIn() {
        return "test";
    }
}
