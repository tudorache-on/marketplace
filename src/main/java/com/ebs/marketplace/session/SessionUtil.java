package com.ebs.marketplace.session;

import com.ebs.marketplace.jwt.JwtUtil;
import com.ebs.marketplace.mapper.UserMapper;
import com.ebs.marketplace.model.JwtRequestLogIn;
import com.ebs.marketplace.model.JwtRequestSignUp;
import com.ebs.marketplace.model.User;
import com.ebs.marketplace.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SessionUtil {

    private final TokenRepository tokenRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public SessionUtil(TokenRepository tokenRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, JwtUserDetailsService jwtUserDetailsService, AuthenticationManager authenticationManager) {
        this.tokenRepository = tokenRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<?> signIn (JwtRequestLogIn jwtRequest){
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtRequest.getUsernameOrEmail());
        String token = jwtUtil.generateToken(userDetails);
        tokenRepository.insert("TOKEN", token);

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

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        tokenRepository.insert("TOKEN", token);

        return ResponseEntity.ok(token);
    }


}
