package com.ebs.marketplace.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequestSignUp {

    private String username;
    private String email;
    private String password;
}
