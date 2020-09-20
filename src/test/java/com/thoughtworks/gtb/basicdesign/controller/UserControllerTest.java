package com.thoughtworks.gtb.basicdesign.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gtb.basicdesign.dto.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ResultActions result;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        User kamil = User.builder()
                .name("KAMIL")
                .age(24L)
                .avatar("https://inews.gtimg.com/newsapp_match/0/3581582328/0")
                .description("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Repellendus, non, dolorem, cumque distinctio magni quam expedita velit laborum sunt amet facere tempora ut fuga aliquam ad asperiores voluptatem dolorum! Quasi.")
                .build();
        String userString = objectMapper.writeValueAsString(kamil);
        mockMvc.perform(post("http://localhost:8080/users")
                .contentType("application/json;charset=UTF-8")
                .content(userString));
    }

    @AfterEach
    void tearDown() throws UnsupportedEncodingException {
        if (result != null) {
            String data = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
            System.out.println(data);
        }
    }

    @Test
    public void should_find_Person_By_1() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("http://localhost:8080/users/1");
        result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("KAMIL")));
    }

    @Test
    public void should_add_user() throws Exception {
        User user = User.builder()
                .name("李四")
                .age(18L)
                .avatar("https://avatars2.githubusercontent.com/u/39233246?s=60&v=4")
                .description("我是李四")
                .build();

        String data = objectMapper.writeValueAsString(user);

        MockHttpServletRequestBuilder request = post("http://localhost:8080/users")
                .contentType("application/json;charset=UTF-8")
                .content(data);
        result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("李四")));

    }

    @Test
    public void should_throw_age_out_of_range() throws Exception {
        User user = User.builder()
                .name("张三")
                .age(16L)
                .avatar("https://avatars2.githubusercontent.com/u/39233246?s=60&v=4")
                .description("我是张三")
                .build();

        String data = objectMapper.writeValueAsString(user);

        MockHttpServletRequestBuilder request = post("http://localhost:8080/users")
                .contentType("application/json;charset=UTF-8")
                .content(data);
        result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].message", Matchers.is("age out of range")));

    }
}