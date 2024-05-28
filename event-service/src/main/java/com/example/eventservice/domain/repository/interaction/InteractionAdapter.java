package com.example.eventservice.domain.repository.interaction;

import com.example.eventservice.domain.entity.interaction.Interaction;
import com.example.eventservice.domain.entity.interaction.LikeStar;
import com.example.eventservice.domain.repository.BaseAdapter;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionAdapter extends BaseAdapter<Interaction, Integer> {
    void deleteInteraction(int culturalEventId, long userId, LikeStar likeStar);

    boolean isLikeStarExist(int culturalEventId, long userId, LikeStar likeStar);

}
