package com.thoughtworks.gtb.basicdesign.repository;

import com.thoughtworks.gtb.basicdesign.entity.EducationEntity;
import com.thoughtworks.gtb.basicdesign.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class EducationRepositoryTest {

    @Autowired
    EducationRepository educationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestEntityManager manager;

    @Test
    void findByUserId() {
        UserEntity userEntity = UserEntity.builder().name("huxiao").build();
        userEntity = manager.persistAndFlush(userEntity);

        EducationEntity educationEntity = EducationEntity.builder()
                .title("aaaabbbb")
                .description("ccccdddd")
                .user(userEntity).build();
        manager.persistAndFlush(educationEntity);

        List<EducationEntity> educations = educationRepository.findByUserId(userEntity.getId());
        Assertions.assertThat(educations.get(0).getTitle()).isEqualTo("aaaabbbb");
    }
}