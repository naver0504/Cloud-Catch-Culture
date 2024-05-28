package com.example.eventservice.domain.repository.interaction;

import com.example.eventservice.domain.entity.interaction.LikeStar;
import com.example.eventservice.domain.entity.interaction.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InteractionRepository extends JpaRepository<Interaction, Integer> {


    @Query("delete from Interaction i where i.culturalEvent.id = :culturalEventId and i.userId = :userId and i.likeStar = :likeStar")
    @Modifying
    void deleteInteraction(int culturalEventId, long userId, LikeStar likeStar);
}
