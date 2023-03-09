package com.guluev.cloudstorage.service;

import com.guluev.cloudstorage.entity.User;
import com.guluev.cloudstorage.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow();
    }

    @Transactional
    public void saveAndFlush(User user) {
        userRepository.saveAndFlush(user);
    }
}
