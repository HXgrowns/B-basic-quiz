package com.thoughtworks.gtb.basicdesign.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gtb.basicdesign.dto.Education;
import com.thoughtworks.gtb.basicdesign.dto.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

// GTB: 有测试，覆盖场景基本是 happy path，已覆盖异常
@SpringBootTest
@AutoConfigureMockMvc
class EducationControllerTest {
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

        Education secondary = Education.builder()
                .year(2005L)
                .title("Secondary school specializing in artistic")
                .description("Eos, explicabo, nam, tenetur et ab eius deserunt aspernatur ipsum ducimus quibusdam quis voluptatibus.")
                .build();
        String secondaryString = objectMapper.writeValueAsString(secondary);
        mockMvc.perform(post("http://localhost:8080/users/1/educations")
                .contentType("application/json;charset=UTF-8")
                .content(secondaryString));

        Education first = Education.builder()
                .year(2009L)
                .title("First level graduation in Graphic Designc")
                .description("Aspernatur, mollitia, quos maxime eius suscipit sed beatae ducimus quaerat quibusdam perferendis? Iusto, quibusdam asperiores unde repellat.")
                .build();
        String firstString = objectMapper.writeValueAsString(first);
        mockMvc.perform(post("http://localhost:8080/users/1/educations")
                .contentType("application/json;charset=UTF-8")
                .content(firstString));
    }

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

        MockHttpServletRequestBuilder request = post("http://localhost:8080/users/1/educations")
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

        MockHttpServletRequestBuilder request = post("http://localhost:8080/users/1/educations")
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

        MockHttpServletRequestBuilder request = post("http://localhost:8080/users/5/educations")
                .contentType("application/json;charset=UTF-8")
                .content(data);
        result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("user not found")));

    }
}