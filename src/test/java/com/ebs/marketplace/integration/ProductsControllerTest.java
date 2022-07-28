package com.ebs.marketplace.integration;

import com.ebs.marketplace.controller.ProductsController;
import com.ebs.marketplace.jwt.JwtAuthenticationEntryPoint;
import com.ebs.marketplace.service.JwtUserDetailsService;
import com.ebs.marketplace.service.ProductService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductsController.class)
public class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    @MockBean
    RedisRepository redisRepository;

    @MockBean
    private TokenUtil tokenUtil;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private SessionUtil sessionUtil;

    @Test
    public void testProducts() throws Exception {
        this.mockMvc.perform(get("/api/products")).andExpect(status().isOk());

        this.mockMvc.perform(patch("/products/{product_id}/{like}", 2, "like"))
                .andExpect(status().isOk());
    }
}
