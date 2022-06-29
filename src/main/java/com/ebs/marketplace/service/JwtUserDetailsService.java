package com.ebs.marketplace.service;

import com.ebs.marketplace.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userMapper.existsByUsernameOrEmail(username, username) != 0) {
            String name = userMapper.findByUsernameOrEmail(username, username).getUsername();
            String password = userMapper.findByUsernameOrEmail(username, username).getPassword();
            return new User(name, password, new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public UserDetails createUser(String name, String password) {
        return new User(name, password, new ArrayList<>());
    }
}
