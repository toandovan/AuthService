package com.example.authservice.service.implement;

import com.example.authservice.dto.CredentialsDto;
import com.example.authservice.entity.User;
import com.example.authservice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.authservice.repository.UserRepository;

import java.nio.CharBuffer;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> signIn(CredentialsDto credentialsDto) {
        String userName=credentialsDto.getUserName();
        Optional<User> user = userRepository.findByUserName(userName);
        if(user.isEmpty()){
            return null;
        }
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.get().getPassword())) {
            return user;
        }else{
            return null;
        }
    }

    public User validateToken(String token) {
        String info = Jwts.parser()
                .setSigningKey("123")
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        Optional<User> userOptional = userRepository.findByUserName(info);

        if (userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();
        return user;
    }

    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUserName());
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, "123")
                .compact();
    }
}
