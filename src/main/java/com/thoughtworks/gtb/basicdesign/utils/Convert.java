package com.thoughtworks.gtb.basicdesign.utils;

import com.thoughtworks.gtb.basicdesign.dto.Education;
import com.thoughtworks.gtb.basicdesign.dto.User;
import com.thoughtworks.gtb.basicdesign.entity.EducationEntity;
import com.thoughtworks.gtb.basicdesign.entity.UserEntity;

public class Convert {
    public static User toUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .age(userEntity.getAge())
                .avatar(userEntity.getAvatar())
                .description(userEntity.getDescription())
                .build();
    }

    public static UserEntity toUserEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .avatar(user.getAvatar())
                .description(user.getDescription())
                .build();
    }

    public static EducationEntity toEducationEntity(Education education) {
        UserEntity userEntity = null;
        if (education.getUser() != null) {
            userEntity = UserEntity.builder().id(education.getUser().getId()).build();
        }
        return EducationEntity.builder()
                .title(education.getTitle())
                .user(userEntity)
                .year(education.getYear())
                .description(education.getDescription())
                .build();
    }

    public static Education toEducation(EducationEntity educationEntity) {
        User user = null;
        if (educationEntity.getUser() != null) {
            user = User.builder().id(educationEntity.getUser().getId()).build();
        }
        return Education.builder()
                .user(user)
                .title(educationEntity.getTitle())
                .description(educationEntity.getDescription())
                .year(educationEntity.getYear())
                .build();
    }
}
