package com.thoughtworks.gtb.basicdesign.service;

import com.thoughtworks.gtb.basicdesign.domain.User;
import com.thoughtworks.gtb.basicdesign.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(int id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.createUser(user);
    }
}
