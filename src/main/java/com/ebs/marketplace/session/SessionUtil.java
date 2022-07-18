package com.ebs.marketplace.session;

import com.ebs.marketplace.mapper.UserMapper;
import com.ebs.marketplace.model.JwtRequestLogIn;
import com.ebs.marketplace.model.JwtRequestSignUp;
import com.ebs.marketplace.model.User;
import com.ebs.marketplace.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    public ResponseEntity<?> signIn (JwtRequestLogIn jwtRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
//                    (jwtRequest.getUsernameOrEmail(), jwtRequest.getPassword()));
//        } catch (BadCredentialsException e) {
//            throw new Exception("Numele sau prenumele este introdus gresit!", e);
//        }

        if (userDetailsService.existsByUsername(jwtRequest.getUsernameOrEmail()))
            request.getSession().setAttribute("JSESSIONID", request.getSession().getId());
        else throw new UsernameNotFoundException("User not found with username: " + jwtRequest.getUsernameOrEmail());

        response.addCookie(new Cookie("NAME", jwtRequest.getUsernameOrEmail()));
        return ResponseEntity.ok(request.getSession().getId());
    }

    public ResponseEntity<?> signUp (JwtRequestSignUp jwtRequest, HttpServletRequest request, HttpServletResponse response) {

        if (userMapper.existsByEmail(jwtRequest.getEmail()) != 0) {
            return new ResponseEntity<>("Emailul dat este ocupat!", HttpStatus.BAD_REQUEST);
        }

        if (userMapper.existsByUsername(jwtRequest.getUsername()) != 0) {
            return new ResponseEntity<>("Numele de utilizator este ocupat!", HttpStatus.BAD_REQUEST);
        }

        User user = new User(jwtRequest.getUsername(), jwtRequest.getEmail(), passwordEncoder.encode(jwtRequest.getPassword()));
        userMapper.insert(user);

        request.getSession().setAttribute("JSESSIONID", request.getSession().getId());
        response.addCookie(new Cookie("NAME", jwtRequest.getUsername()));

        return ResponseEntity.ok(request.getSession().getId());
    }


}
