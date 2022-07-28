package com.ebs.marketplace;

import com.ebs.marketplace.controller.JwtAuthenticationController;
import com.ebs.marketplace.controller.UserController;
import com.ebs.marketplace.jwt.JwtAuthenticationEntryPoint;
import com.ebs.marketplace.model.JwtRequestLogIn;
import com.ebs.marketplace.model.JwtRequestSignUp;
import com.ebs.marketplace.model.Product;
import com.ebs.marketplace.model.ProductDto;
import com.ebs.marketplace.service.JwtUserDetailsService;
import com.ebs.marketplace.service.ProductService;
import com.ebs.marketplace.session.RedisRepository;
import com.ebs.marketplace.session.SessionUtil;
import com.ebs.marketplace.session.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
public class ProductControllerTest {
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

    @MockBean
    private ProductService productService;

    @Test
    @WithMockUser(value = "ion")
    public void testGetProducts() throws Exception {
        this.mockMvc.perform(get("/api/user/products")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "ion")
    public void testPostProducts() throws Exception {
        ProductDto product = new ProductDto("Piersic", "bun", 345);

        this.mockMvc.perform(post("/api/user/products")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "ion")
    public void testPutProducts() throws Exception {
        ProductDto product = new ProductDto("Piersic", "bun", 345);

        this.mockMvc.perform(put("/api/user/products/{product_id}", 1)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "ion")
    public void testDeleteProducts() throws Exception {
        this.mockMvc.perform(delete("/api/user/products/{product_id}", 1))
                .andExpect(status().isOk());
    }
}
