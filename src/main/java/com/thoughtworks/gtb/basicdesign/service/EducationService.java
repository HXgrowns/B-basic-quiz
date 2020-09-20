package com.thoughtworks.gtb.basicdesign.service;

import com.thoughtworks.gtb.basicdesign.domain.Education;
import com.thoughtworks.gtb.basicdesign.domain.User;
import com.thoughtworks.gtb.basicdesign.exception.BusinessException;
import com.thoughtworks.gtb.basicdesign.exception.ExceptionEnum;
import com.thoughtworks.gtb.basicdesign.repository.EducationRespository;
import com.thoughtworks.gtb.basicdesign.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EducationService {
    private EducationRespository educationRespository;
    private UserRepository userRepository;

    public EducationService(EducationRespository educationRespository, UserRepository userRepository) {
        this.educationRespository = educationRespository;
        this.userRepository = userRepository;
    }

    // GTB: 可以让 Repository 完成过滤的工作,已用注解过滤
    public List<Education> findByUserId(Long userId) {
        return educationRespository.getEducations()
                .stream()
                .filter(education -> userId.equals(education.getUserId()))
                .collect(Collectors.toList());
    }

    public Education createEducation(Long userId, Education education) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new BusinessException(ExceptionEnum.USER_NOT_FOUND);
        }

        education.setUserId(userId);
        return educationRespository.createEducation(education);
    }
}
