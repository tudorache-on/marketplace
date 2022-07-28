package com.ebs.marketplace.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtRequestSignUp {
    private String username;
    private String email;
    private String password;
}
