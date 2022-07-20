package com.ebs.marketplace.session;

import com.ebs.marketplace.mapper.UserMapper;
import com.ebs.marketplace.model.JwtRequestLogIn;
import com.ebs.marketplace.model.JwtRequestSignUp;
import com.ebs.marketplace.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class SessionUtil {

    private final RedisRepository redisRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SessionUtil(RedisRepository redisRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.redisRepository = redisRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> signIn(JwtRequestLogIn jwtRequest) {
        User user = userMapper.findByUsernameOrEmail(jwtRequest.getUsernameOrEmail(), jwtRequest.getUsernameOrEmail());

        String token = tokenGenerator(user);

        redisRepository.save(String.valueOf(user.getId()), token);

        return ResponseEntity.ok(token);
    }

    public ResponseEntity<?> signUp(JwtRequestSignUp jwtRequest) {

        if (userMapper.existsByEmail(jwtRequest.getEmail()) != 0) {
            return new ResponseEntity<>("Emailul dat este ocupat!", HttpStatus.BAD_REQUEST);
        }

        if (userMapper.existsByUsername(jwtRequest.getUsername()) != 0) {
            return new ResponseEntity<>("Numele de utilizator este ocupat!", HttpStatus.BAD_REQUEST);
        }

        User user = new User(jwtRequest.getUsername(), jwtRequest.getEmail(), passwordEncoder.encode(jwtRequest.getPassword()));
        userMapper.insert(user);

        String token = tokenGenerator(user);

        redisRepository.save(String.valueOf(user.getId()), token);

        return ResponseEntity.ok(token);
    }

    public String tokenGenerator(User user) {
        Base64.Encoder encoder = Base64.getMimeEncoder();
        String eId = encoder.encodeToString(String.valueOf(user.getId()).getBytes());
        String eUsername = encoder.encodeToString(user.getUsername().getBytes());
        String eDate = encoder.encodeToString(String.valueOf(System.currentTimeMillis()).getBytes());
        return eId + "." + eUsername + "." + user.getPassword() + "." + eDate;
    }


}
