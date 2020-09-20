package com.thoughtworks.gtb.basicdesign.repository;

import com.thoughtworks.gtb.basicdesign.dto.Education;
import com.thoughtworks.gtb.basicdesign.entity.EducationEntity;
import com.thoughtworks.gtb.basicdesign.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<EducationEntity, Long> {
    List<EducationEntity> findByUserId(Long userId);
}
