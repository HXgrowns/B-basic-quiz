package com.thoughtworks.gtb.basicdesign.controller;

import com.thoughtworks.gtb.basicdesign.domain.Education;
import com.thoughtworks.gtb.basicdesign.service.EducationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// GTB: - 没有任何校验的实现吗？
@RestController
public class EducationController {
    private EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    // GTB: - 可以提取到 class 上去
    @GetMapping("/users/{userId}/educations")
    public ResponseEntity<List<Education>> findByUserId(@PathVariable int userId) {
        return ResponseEntity.ok().body(educationService.findByUserId(userId));
    }

    @PostMapping("/users/{userId}/educations")
    public ResponseEntity<Education> createEducation(@PathVariable int userId, @RequestBody Education education) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(educationService.createEducation(userId, education));
    }
}
