package com.ebs.marketplace.controller;

import com.ebs.marketplace.mappers.UserMapper;
import com.ebs.marketplace.model.JwtRequestSignUp;
import com.ebs.marketplace.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.ebs.marketplace.service.JwtUserDetailsService;

import com.ebs.marketplace.jwt.JwtTokenUtil;
import com.ebs.marketplace.model.JwtRequestLogIn;
import com.ebs.marketplace.model.JwtResponse;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class JwtAuthenticationController {

    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;

    private JwtUserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder;

    private UserMapper userMapper;

    @PostMapping("/signin")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestLogIn jwtRequest) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (jwtRequest.getUsernameOrEmail(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Numele sau prenumele este introdus gresit!", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsernameOrEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody JwtRequestSignUp jwtRequest) throws Exception {

        if (userMapper.existsByEmail(jwtRequest.getEmail()) != 0) {
            return new ResponseEntity<>("Emailul dat este ocupat!", HttpStatus.BAD_REQUEST);
        }

        if (userMapper.existsByUsername(jwtRequest.getUsername()) != 0) {
            return new ResponseEntity<>("Numele de utilizator este ocupat!", HttpStatus.BAD_REQUEST);
        }

        User user = new User(jwtRequest.getUsername(), jwtRequest.getEmail(), passwordEncoder.encode(jwtRequest.getPassword()));
        userMapper.insert(user);

        final UserDetails userDetails = userDetailsService.createUser(jwtRequest.getUsername(), jwtRequest.getPassword());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
