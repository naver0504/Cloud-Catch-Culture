package com.example.eventservice.repository.interaction;

import com.example.eventservice.entity.interaction.Interaction;
import com.example.eventservice.entity.interaction.LikeStar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {


    @Query("delete from Interaction i where i.culturalEvent.id = :culturalEventId and i.userId = :userId and i.likeStar = :likeStar")
    @Modifying
    void deleteInteraction(int culturalEventId, long userId, LikeStar likeStar);
}
