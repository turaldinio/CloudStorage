package com.guluev.cloudstorage.service;

import com.guluev.cloudstorage.entity.User;
import com.guluev.cloudstorage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserByEmail(String email) {
       return userRepository.findUserByEmail(email).orElseThrow();
    }
}
