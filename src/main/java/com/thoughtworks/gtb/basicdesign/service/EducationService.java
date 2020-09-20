package com.thoughtworks.gtb.basicdesign.service;

import com.thoughtworks.gtb.basicdesign.dto.Education;
import com.thoughtworks.gtb.basicdesign.dto.User;
import com.thoughtworks.gtb.basicdesign.entity.EducationEntity;
import com.thoughtworks.gtb.basicdesign.exception.BusinessException;
import com.thoughtworks.gtb.basicdesign.exception.ExceptionEnum;
import com.thoughtworks.gtb.basicdesign.repository.EducationRepository;
import com.thoughtworks.gtb.basicdesign.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EducationService {
    private EducationRepository educationRepository;
    private UserRepository userRepository;

    public EducationService(EducationRepository educationRepository, UserRepository userRepository) {
        this.educationRepository = educationRepository;
        this.userRepository = userRepository;
    }

    // GTB: 可以让 Repository 完成过滤的工作,已用注解过滤
    public List<Education> findByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND));
        return educationRepository.findByUserId(userId).stream().map(EducationEntity::toEducation).collect(Collectors.toList());
    }

    @Transactional
    public Education createEducation(Long userId, Education education) {
        userRepository.findById(userId).orElseThrow(() -> new BusinessException(ExceptionEnum.USER_NOT_FOUND));
        education.setUser(User.builder().id(userId).build());
        return educationRepository.save(education.toEducationEntity()).toEducation();
    }
}
