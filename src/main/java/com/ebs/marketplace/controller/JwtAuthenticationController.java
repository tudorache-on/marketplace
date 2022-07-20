package com.ebs.marketplace.controller;

import com.ebs.marketplace.session.SessionUtil;
import com.ebs.marketplace.model.JwtRequestLogIn;
import com.ebs.marketplace.model.JwtRequestSignUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class JwtAuthenticationController {

    private final SessionUtil sessionUtil;


    @Autowired
    public JwtAuthenticationController(SessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestLogIn jwtRequest){
        return sessionUtil.signIn(jwtRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody JwtRequestSignUp jwtRequest){
        return sessionUtil.signUp(jwtRequest);
    }
}
