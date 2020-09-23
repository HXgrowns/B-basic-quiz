package com.thoughtworks.gtb.basicdesign.service;

import com.thoughtworks.gtb.basicdesign.dto.Education;
import com.thoughtworks.gtb.basicdesign.dto.User;
import com.thoughtworks.gtb.basicdesign.entity.EducationEntity;
import com.thoughtworks.gtb.basicdesign.entity.UserEntity;
import com.thoughtworks.gtb.basicdesign.repository.EducationRepository;
import com.thoughtworks.gtb.basicdesign.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EducationServiceTest {
    @Mock
    private EducationRepository educationRepository;
    @Mock
    private UserRepository userRepository;
    private EducationService educationService;

    @BeforeEach
    void setUp() {
        educationService = new EducationService(educationRepository,userRepository);
    }

    @Test
    void findByUserId() {
        List<EducationEntity> educationEntities = Arrays.asList(EducationEntity.builder().title("aaaabbbb").build(), EducationEntity.builder().title("bbbbcccc").build());
        when(userRepository.findById(1L)).thenReturn(Optional.of(UserEntity.builder().build()));
        when(educationRepository.findByUserId(1L)).thenReturn(educationEntities);

        List<Education> result = educationService.findByUserId(1L);
        Assertions.assertThat(educationEntities.get(0).getTitle()).isEqualTo(result.get(0).getTitle());
        Assertions.assertThat(educationEntities.get(1).getTitle()).isEqualTo(result.get(1).getTitle());
    }

    @Test
    void createEducation() {
        UserEntity userEntity = UserEntity.builder().id(1L).build();
        User user = User.builder().id(1L).build();
        EducationEntity educationEntity = EducationEntity.builder().title("aaaabbbb").user(userEntity).build();
        Education education = Education.builder().title("aaaabbbb").user(user).build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(educationRepository.save(educationEntity)).thenReturn(educationEntity);

        Education newEducation = educationService.createEducation(1L, education);
        Assertions.assertThat(newEducation.getTitle()).isEqualTo("aaaabbbb");
    }
}
