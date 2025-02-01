package com.example.baseballcommunitybackend.service;

import com.example.baseballcommunitybackend.document.User;
import com.example.baseballcommunitybackend.repository.UserRepository;
import com.mongodb.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(User user) {
        try {
            if (userRepository.findByUserId(user.getUserId()).isPresent()) {
                throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } catch (DuplicateKeyException e) {
            throw e;
        }
    }

//    public Optional<User> findByUserId(String userId) {
//        return userRepository.findByUserId(userId);
//    }

    public boolean authenticateUser(String userId, String rawPassword) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        return userOptional.isPresent() && passwordEncoder.matches(rawPassword, userOptional.get().getPassword());
    }

}


