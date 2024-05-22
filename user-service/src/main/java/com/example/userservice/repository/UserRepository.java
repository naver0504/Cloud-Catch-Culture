package com.example.userservice.repository;

import com.example.userservice.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("update User u  set u.nickname = :nickname where u.id = :userId")
    @Modifying(clearAutomatically = true)
    void updateUserNickname(long userId, String nickname);

    @Query("update User u set u.point = u.point + :point where u.id = :userId")
    @Modifying(clearAutomatically = true)
    void updateUserPoint(long userId, int point);

    @Query("select u from User u where u.id in :userIds")
    List<User> findUserByIds(List<Long> userIds);


}
