package com.thoughtworks.gtb.basicdesign.service;

import com.thoughtworks.gtb.basicdesign.dto.User;
import com.thoughtworks.gtb.basicdesign.entity.UserEntity;
import com.thoughtworks.gtb.basicdesign.repository.UserRepository;
import com.thoughtworks.gtb.basicdesign.utils.Convert;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseGet(null);
        return Convert.toUser(userEntity);
    }

    public User createUser(User user) {
        UserEntity userEntity = userRepository.save(Convert.toUserEntity(user));
        return Convert.toUser(userEntity);
    }
}
