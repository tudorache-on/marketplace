package com.ebs.marketplace.service;

import com.ebs.marketplace.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserMapper userMapper;

    @Autowired
    public JwtUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userMapper.existsByUsernameOrEmail(username, username) == 0)
            throw new UsernameNotFoundException("User not found with username: " + username);

        String name = userMapper.findByUsernameOrEmail(username, username).getUsername();
        String password = userMapper.findByUsernameOrEmail(username, username).getPassword();
        return new User(name, password, new ArrayList<>());
    }
}
