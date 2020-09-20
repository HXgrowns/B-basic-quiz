package com.thoughtworks.gtb.basicdesign.service;

import com.thoughtworks.gtb.basicdesign.dto.User;
import com.thoughtworks.gtb.basicdesign.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseGet(null).toUser();
    }

    public  User createUser(User user) {
        return userRepository.save(user.toUserEntity()).toUser();
    }
}
