package com.example.userservice.repository;

import com.example.userservice.entity.point_history.PointHistory;
import com.example.userservice.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Integer> {

    @Query("delete from PointHistory ph where ph.messageId = :messageId")
    @Modifying
    void deleteByMessageId(long messageId);

    Optional<PointHistory> findByMessageId(long messageId);

    @Query("select u from PointHistory ph inner join User u on u.id = ph.user.id where ph.user.id = :userId and  ph.messageId = :messageId")
    Optional<PointHistory> findByUserIdAndPointHistoryMessageId(long userId, long messageId);

}
