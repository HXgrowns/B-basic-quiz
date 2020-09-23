package com.thoughtworks.gtb.basicdesign.service;

import com.thoughtworks.gtb.basicdesign.dto.User;
import com.thoughtworks.gtb.basicdesign.entity.UserEntity;
import com.thoughtworks.gtb.basicdesign.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.assertj.core.api.Assertions;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void findById() {
        UserEntity user = UserEntity.builder().name("zhangsan").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User newUser = userService.findById(1L);
        Assertions.assertThat(newUser.getName()).isEqualTo("zhangsan");
    }

    @Test
    void createUser() {
        UserEntity userEntity = UserEntity.builder().name("zhangsan").build();
        User user = User.builder().name("zhangsan").build();
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        User newUser = userService.createUser(user);
        Assertions.assertThat(newUser.getName()).isEqualTo("zhangsan");
    }
}
