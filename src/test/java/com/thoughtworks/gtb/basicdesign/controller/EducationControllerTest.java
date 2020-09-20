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

// GTB: 有测试，覆盖场景基本是 happy path，已覆盖异常
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

    // GTB: - 测试名字风格怎么都不统一？已统一
    @Test
    void should_find_educations_by_userId() throws Exception {
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
                .year(2020L)
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

    @Test
    public void should_throw_title_is_null() throws Exception {
        Education education = Education.builder().title(null)
                .description("bbbb")
                .year(2020L)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(education);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("http://localhost:8080/users/1/educations")
                .contentType("application/json;charset=UTF-8")
                .content(data);
        result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].message", Matchers.is("title is null")));

    }

    @Test
    public void should_throw_user_not_found() throws Exception {
        Education education = Education.builder().title("aaaaaaaaaaaaaa")
                .description("bbbb")
                .year(2020L)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(education);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("http://localhost:8080/users/5/educations")
                .contentType("application/json;charset=UTF-8")
                .content(data);
        result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("user not found")));

    }
}