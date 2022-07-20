package com.ebs.marketplace.session;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class TokenUtil {
    Base64.Decoder decoder = Base64.getMimeDecoder();

    public String getId(String[] claims) {
        return new String(decoder.decode(claims[0]));
    }

    public String getUsername(String[] claims) {
        return new String(decoder.decode(claims[1]));
    }

    public String getPassword(String[] claims) {
        return claims[2];
    }

    public String getDate(String[] claims) {
        return new String(decoder.decode(claims[3]));
    }
}
