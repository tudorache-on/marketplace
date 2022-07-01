package com.ebs.marketplace.controller;

import com.ebs.marketplace.model.JwtRequestLogIn;
import com.ebs.marketplace.model.JwtRequestSignUp;
import com.ebs.marketplace.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class JwtAuthenticationController {

    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    public JwtAuthenticationController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestLogIn jwtRequest) throws Exception {
        return userAuthenticationService.createAuthenticationToken(jwtRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody JwtRequestSignUp jwtRequest){
        return userAuthenticationService.createNewUser(jwtRequest);
    }
}
