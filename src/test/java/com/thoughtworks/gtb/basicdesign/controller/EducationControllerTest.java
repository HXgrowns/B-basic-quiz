package com.thoughtworks.gtb.basicdesign.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gtb.basicdesign.domain.Education;
import com.thoughtworks.gtb.basicdesign.domain.User;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class EducationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ResultActions result;

    @AfterEach
    void tearDown() throws UnsupportedEncodingException {
        if (result != null) {
            MvcResult mvcResult = result.andReturn();
            String data = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
            System.out.println(data);
        }
    }

    @Test
    void findByUserId() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("http://localhost:8080/users/1/educations");
        result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title",
                        Matchers.is("Secondary school specializing in artistic")));
    }

    @Test
    public void should_add_education() throws Exception {
        Education education = Education.builder().title("aaa")
                .description("bbbb")
                .year(2020)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(education);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("http://localhost:8080/users/1/educations")
                .contentType("application/json;charset=UTF-8")
                .content(data);
        result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.year", Matchers.is(2020)));

    }
}