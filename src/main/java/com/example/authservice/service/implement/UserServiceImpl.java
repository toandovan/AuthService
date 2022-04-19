package com.example.authservice.service.implement;

import com.example.authservice.dto.CredentialsDto;
import com.example.authservice.entity.User;
import com.example.authservice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.authservice.repository.UserRepository;

import java.nio.CharBuffer;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String secretKey="123";
    public String signIn(CredentialsDto credentialsDto) {
        String userName=credentialsDto.getUserName();
        List<User> listUser= userRepository.findByUserNameIs(userName);
        User user = listUser.size()!=0?listUser.get(0):null;

        if(user.equals(null)){
            return null;
        }
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            String token= createToken(user);
            return token;
        }else{
            return null;
        }
    }

    public String createToken(User user) {
        return Jwts.builder()
                .claim("username",user.getUserName())
                .claim("id",user.getId())
                .claim("role",user.getRole())
                .signWith(HS256, Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .compact();
    }
}
