package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findByFirstNameLastName(String firstName, String lastName) {
        User user = userRepository.findByFirstNameAndLastName(firstName, lastName);
        return user;
    }
}
