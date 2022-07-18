package com.ebs.marketplace.session;

import com.ebs.marketplace.mapper.UserMapper;
import com.ebs.marketplace.model.JwtRequestLogIn;
import com.ebs.marketplace.model.JwtRequestSignUp;
import com.ebs.marketplace.model.Token;
import com.ebs.marketplace.model.User;
import com.ebs.marketplace.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SessionUtil {

    private final SessionRepository sessionRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SessionUtil(SessionRepository sessionRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.sessionRepository = sessionRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> signIn (JwtRequestLogIn jwtRequest) throws Exception {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
//                    (jwtRequest.getUsernameOrEmail(), jwtRequest.getPassword()));
//        } catch (BadCredentialsException e) {
//            throw new Exception("Numele sau prenumele este introdus gresit!", e);
//        }
        String token;
        User user = userMapper.findByUsernameOrEmail(jwtRequest.getUsernameOrEmail(), jwtRequest.getUsernameOrEmail());

        if (user == null)
            throw new UsernameNotFoundException("User not found with username: " + jwtRequest.getUsernameOrEmail());
        else {
            token = passwordEncoder.encode(user.getId() + user.getUsername());
        }

        sessionRepository.insert("TOKEN", token);

        return ResponseEntity.ok(token);
    }

    public ResponseEntity<?> signUp (JwtRequestSignUp jwtRequest) {

        if (userMapper.existsByEmail(jwtRequest.getEmail()) != 0) {
            return new ResponseEntity<>("Emailul dat este ocupat!", HttpStatus.BAD_REQUEST);
        }

        if (userMapper.existsByUsername(jwtRequest.getUsername()) != 0) {
            return new ResponseEntity<>("Numele de utilizator este ocupat!", HttpStatus.BAD_REQUEST);
        }

        User user = new User(jwtRequest.getUsername(), jwtRequest.getEmail(), passwordEncoder.encode(jwtRequest.getPassword()));
        userMapper.insert(user);

        String token = passwordEncoder.encode(user.getId() + user.getUsername());

        sessionRepository.insert("TOKEN", token);

        return ResponseEntity.ok(token);
    }


}
