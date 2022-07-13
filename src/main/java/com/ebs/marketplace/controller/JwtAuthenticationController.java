package com.ebs.marketplace.controller;

import com.ebs.marketplace.jwt.SessionUtil;
import com.ebs.marketplace.model.JwtRequestLogIn;
import com.ebs.marketplace.model.JwtRequestSignUp;
import com.ebs.marketplace.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestLogIn jwtRequest, HttpServletRequest request) throws Exception {
        return sessionUtil.logIn(jwtRequest, request);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody JwtRequestSignUp jwtRequest, HttpServletRequest request){
        return sessionUtil.signUp(jwtRequest, request);
    }
}
