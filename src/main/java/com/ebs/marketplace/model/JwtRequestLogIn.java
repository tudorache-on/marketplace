package com.ebs.marketplace.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequestLogIn {
    private String usernameOrEmail;
    private String password;
}
