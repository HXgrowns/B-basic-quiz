package com.thoughtworks.gtb.basicdesign.service;

import com.thoughtworks.gtb.basicdesign.domain.Education;
import com.thoughtworks.gtb.basicdesign.repository.EducationRespository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EducationService {
    private EducationRespository educationRespository;

    public EducationService(EducationRespository educationRespository) {
        this.educationRespository = educationRespository;
    }

    public List<Education> findByUserId(int userId) {
        return educationRespository.getEducations()
                .stream()
                .filter(education -> userId == education.getUserId())
                .collect(Collectors.toList());
    }
}
