package com.ebs.marketplace;

import com.ebs.marketplace.controller.JwtAuthenticationController;
import com.ebs.marketplace.jwt.JwtAuthenticationEntryPoint;
import com.ebs.marketplace.model.JwtRequestSignUp;
import com.ebs.marketplace.service.JwtUserDetailsService;
import com.ebs.marketplace.session.RedisRepository;
import com.ebs.marketplace.session.SessionUtil;
import com.ebs.marketplace.session.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = JwtAuthenticationController.class)
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RedisRepository redisRepository;

    @MockBean
    private TokenUtil tokenUtil;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private SessionUtil sessionUtil;

    @Test
    public void testAuth() throws Exception {
        JwtRequestSignUp user = new JwtRequestSignUp("tudor", "tudor", "tudor");

        this.mockMvc.perform(post("/api/auth/signup")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/api/auth/signin")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

}

