package com.example.userservice.service;

import com.example.userservice.dto.UserNicknameForFeign;
import com.example.userservice.entity.user.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findById(final long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public int findUserPointByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found")).getPoint();
    }

    @Transactional
    public void updateUserNickname(final long userId, final String nickname) {
        userRepository.updateUserNickname(userId, nickname);
    }

    public List<UserNicknameForFeign> findUserNicknameByIds(final List<Long> userIds) {
        return userRepository.findUserByIds(userIds).stream()
                .map(user -> new UserNicknameForFeign(user.getId(), user.getNickname()))
                .toList();
    }


}
