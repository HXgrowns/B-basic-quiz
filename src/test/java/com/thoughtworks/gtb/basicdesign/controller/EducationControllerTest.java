package com.thoughtworks.gtb.basicdesign.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gtb.basicdesign.constant.ErrorMessage;
import com.thoughtworks.gtb.basicdesign.dto.Education;
import com.thoughtworks.gtb.basicdesign.service.EducationService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(EducationController.class)
@AutoConfigureJsonTesters
class EducationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EducationService educationService;
    @Autowired
    private JacksonTester<Education> educationJacksonTester;

    @Nested
    class GetEducationByUserId {
        private List<Education> educationList = new ArrayList<>();
        private Education education;

        @BeforeEach
        void setUp() throws Exception {
            Education secondary = Education.builder()
                    .year(2005L)
                    .title("Secondary school specializing in artistic")
                    .description("Eos, explicabo, nam, tenetur et ab eius deserunt aspernatur ipsum ducimus quibusdam quis voluptatibus.")
                    .build();
            education = secondary;
            educationList.add(secondary);
            Education first = Education.builder()
                    .year(2009L)
                    .title("First level graduation in Graphic Designc")
                    .description("Aspernatur, mollitia, quos maxime eius suscipit sed beatae ducimus quaerat quibusdam perferendis? Iusto, quibusdam asperiores unde repellat.")
                    .build();
            educationList.add(first);
        }

        @AfterEach
        void tearDown() throws UnsupportedEncodingException {
            Mockito.reset(educationService);
        }

        @Nested
        class WhenUserIdExists {
            @Test
            public void should_return_education_by_user_id_with_jsonPath() throws Exception {
                when(educationService.findByUserId(1L)).thenReturn(educationList);

                mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/users/1/educations"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].title",
                                Matchers.is("Secondary school specializing in artistic")));
                verify(educationService, times(1)).findByUserId(1L);
            }

            @Test
            public void should_add_education_user_id_with_jacksontester() throws Exception {
                when(educationService.findByUserId(1L)).thenReturn(educationList);

                MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/users/1/educations"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse();

                verify(educationService).findByUserId(1L);
            }
        }

    }

    @Nested
    class CreateEducation {
        private Education newEducation;

        @BeforeEach
        void setUp() {
            newEducation = Education.builder().title("aaaaaaaaaa")
                    .description("bbbb")
                    .year(2020L)
                    .build();
        }

        @Nested
        class WhenEducationIsValid {
            @Test
            public void should_add_education_with_jsonPath() throws Exception {
                when(educationService.createEducation(1L, newEducation)).thenReturn(newEducation);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/users/1/educations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(educationJacksonTester.write(newEducation).getJson());

                mockMvc.perform(requestBuilder)
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.year", is(2020)))
                        .andExpect(jsonPath("$.title", is("aaaaaaaaaa")))
                        .andExpect(jsonPath("$.description", is("bbbb")))
                ;

                verify(educationService).createEducation(1L, newEducation);
            }

        }

        @Nested
        class WhenUserIdNotExists {
            @Test
            public void should_throw_when_title_is_null_with_jsonPath() throws Exception {
                Education titleIsNUllEducation = Education.builder().title(null)
                        .description("bbbb")
                        .year(2020L)
                        .build();

                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/users/1/educations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(educationJacksonTester.write(titleIsNUllEducation).getJson());

                mockMvc.perform(requestBuilder)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message", is(ErrorMessage.TITLE_IS_EMPTY)));
            }
        }
    }
}