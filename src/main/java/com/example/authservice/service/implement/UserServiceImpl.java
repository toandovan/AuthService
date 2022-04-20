package com.example.authservice.service.implement;

import com.example.authservice.dto.CredentialsDto;
import com.example.authservice.dto.UserDto;
import com.example.authservice.entity.User;
import com.example.authservice.service.UserService;
import io.jsonwebtoken.Jwts;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.authservice.repository.UserRepository;
import java.nio.CharBuffer;
import java.util.Base64;
import java.util.List;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String secretKey="123";

    public UserDto signIn(CredentialsDto credentialsDto) {
        String userName=credentialsDto.getUserName();
        List<User> listUser= userRepository.findByUserNameIs(userName);
        User user = listUser.size()!=0?listUser.get(0):null;

        if(user.equals(null)){
            return null;
        }
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            String token= createToken(user);
            UserDto userDto=new UserDto(user.getId(),user.getUserName(),token,user.getRole());
            return userDto;
        }else{
            return null;
        }
    }

    public UserDto getUserData(String token){
        String[] chunks = token.split("\\.");
        String payload = chunks[1];
        byte[] temp= Base64.getDecoder().decode(payload);
        String data=new String(temp);
        JsonObject jsonpObject=new JsonParser().parse(data).getAsJsonObject();
        UserDto userDto=new UserDto(jsonpObject.get("id").getAsInt(),
                jsonpObject.get("username").getAsString(),
                token,
                jsonpObject.get("role").getAsString());
        return userDto;
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
