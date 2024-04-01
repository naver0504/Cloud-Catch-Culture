package com.example.userservice.service;

import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public int findUserPointByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found")).getPoint();
    }

    @Transactional
    public void updateUserNickname(final String email, final String nickname) {
        userRepository.updateUserNickname(email, nickname);
    }


}
