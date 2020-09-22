package com.thoughtworks.gtb.basicdesign.controller;

import com.thoughtworks.gtb.basicdesign.constant.ErrorMessage;
import com.thoughtworks.gtb.basicdesign.dto.User;
import com.thoughtworks.gtb.basicdesign.service.UserService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
@AutoConfigureJsonTesters
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private JacksonTester<User> userJacksonTester;

    @Nested
    class GetUserById {

        @AfterEach
        void tearDown() throws UnsupportedEncodingException {
            Mockito.reset(userService);
        }

        @Nested
        class GetUser {
            @Test
            public void should_return_education_by_user_id_with_jsonPath() throws Exception {
                when(userService.findById(1L)).thenReturn(User.builder().id(1L).name("zhangsan").build());

                mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/users/1"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id", is(1)))
                        .andExpect(jsonPath("$.name", is("zhangsan")));
                verify(userService, times(1)).findById(1L);
            }

            @Test
            public void should_add_education_user_id_with_jacksontester() throws Exception {
                when(userService.findById(1L)).thenReturn(null);

                mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/users/1"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(""));

                verify(userService).findById(1L);
            }
        }

    }

    @Nested
    class CreateUser {
        private User newUser;

        @BeforeEach
        void setUp() {
            newUser = User.builder().name("lisi")
                    .age(18L)
                    .avatar("aaaabbbb")
                    .description("desc")
                    .build();
        }

        @Nested
        class WhenEducationIsValid {
            @Test
            public void should_add_education_with_jsonPath() throws Exception {
                when(userService.createUser(newUser)).thenReturn(newUser);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJacksonTester.write(newUser).getJson());

                mockMvc.perform(requestBuilder)
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.age", is(18)))
                        .andExpect(jsonPath("$.name", is("lisi")));

                verify(userService).createUser(newUser);
            }
        }

        @Nested
        class WhenUserIdNotExists {
            @Test
            public void should_throw_when_title_is_null_with_jsonPath() throws Exception {
                User titleIsNUllUser = User.builder()
                        .age(10L)
                        .name("lisi")
                        .avatar("aaaabbbb")
                        .description("cccc").build();

                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJacksonTester.write(titleIsNUllUser).getJson());

                mockMvc.perform(requestBuilder)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message", is(ErrorMessage.AGE_OUT_OF_RANGE)));
            }
        }
    }
}