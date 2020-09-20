package com.thoughtworks.gtb.basicdesign.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gtb.basicdesign.domain.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ResultActions result;

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
        User user = User.builder().name("张三")
                .age(18L)
                .avatar("https://avatars2.githubusercontent.com/u/39233246?s=60&v=4")
                .description("我是张三").build();

        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(user);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("http://localhost:8080/users")
                .contentType("application/json;charset=UTF-8")
                .content(data);
        result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("张三")));

    }
}