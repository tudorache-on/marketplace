package com.ebs.marketplace.jwt;

import com.ebs.marketplace.service.JwtUserDetailsService;
import com.ebs.marketplace.session.SessionRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final SessionRepository sessionRepository;

    @Autowired
    public JwtRequestFilter(JwtUserDetailsService jwtUserDetailsService, SessionRepository sessionRepository) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.sessionRepository = sessionRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

//        final String requestTokenHeader = request.getHeader("Cookie");
//
//
//        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//            jwtToken = requestTokenHeader.substring(7);
//            try {
//                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
//            } catch (IllegalArgumentException e) {
//                System.out.println("Unable to get JWT Token");
//            } catch (ExpiredJwtException e) {
//                System.out.println("JWT Token has expired");
//            }
//        }

        Optional<String> username = (Arrays.stream(request.getCookies())
                .filter(cookie -> "NAME".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findAny());

        Optional<String> cookieId = (Arrays.stream(request.getCookies())
                .filter(cookie -> "JSESSIONID".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findAny());

        if (SecurityContextHolder.getContext().getAuthentication() == null && username.isPresent()) {

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username.get());

            if (sessionRepository.existsBySessionId(cookieId.get())) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
