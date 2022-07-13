package com.ebs.marketplace.jwt;

import com.ebs.marketplace.mapper.UserMapper;
import com.ebs.marketplace.model.JwtRequestLogIn;
import com.ebs.marketplace.model.JwtRequestSignUp;
import com.ebs.marketplace.model.User;
import com.ebs.marketplace.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SessionUtil {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SessionUtil(AuthenticationManager authenticationManager, JwtUserDetailsService userDetailsService, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> logIn (JwtRequestLogIn jwtRequest, HttpServletRequest request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (jwtRequest.getUsernameOrEmail(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Numele sau prenumele este introdus gresit!", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsernameOrEmail());

        request.getSession().setAttribute("JSESSIONID", request.getSession().getId());
        request.getSession().setAttribute("NAME", userDetails.getUsername());

        return ResponseEntity.ok(request.getSession().getId());
    }

    public ResponseEntity<?> signUp (JwtRequestSignUp jwtRequest, HttpServletRequest request) {

        if (userMapper.existsByEmail(jwtRequest.getEmail()) != 0) {
            return new ResponseEntity<>("Emailul dat este ocupat!", HttpStatus.BAD_REQUEST);
        }

        if (userMapper.existsByUsername(jwtRequest.getUsername()) != 0) {
            return new ResponseEntity<>("Numele de utilizator este ocupat!", HttpStatus.BAD_REQUEST);
        }

        User user = new User(jwtRequest.getUsername(), jwtRequest.getEmail(), passwordEncoder.encode(jwtRequest.getPassword()));
        userMapper.insert(user);

        request.getSession().setAttribute("JSESSIONID", request.getSession().getId());
        return ResponseEntity.ok(request.getSession().getId());
    }

}
