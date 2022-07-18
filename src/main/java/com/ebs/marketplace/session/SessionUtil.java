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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SessionUtil {

    private final SessionRepository sessionRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public SessionUtil(SessionRepository sessionRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, JwtUserDetailsService jwtUserDetailsService) {
        this.sessionRepository = sessionRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    public ResponseEntity<?> signIn (JwtRequestLogIn jwtRequest){
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
//                    (jwtRequest.getUsernameOrEmail(), jwtRequest.getPassword()));
//        } catch (BadCredentialsException e) {
//            throw new Exception("Numele sau prenumele este introdus gresit!", e);
//        }
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtRequest.getUsernameOrEmail());
        String token = jwtUtil.generateToken(userDetails);
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

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        sessionRepository.insert("TOKEN", token);

        return ResponseEntity.ok(token);
    }


}
