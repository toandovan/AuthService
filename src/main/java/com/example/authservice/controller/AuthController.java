package com.example.authservice.controller;

import com.example.authservice.dto.CredentialsDto;
import com.example.authservice.entity.User;
import com.example.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signIn")
    public ResponseEntity<Object> signIn(@RequestBody CredentialsDto credentialsDto) {
        String token=userService.signIn(credentialsDto);
        if (token.equals(null)){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
        else
            return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
