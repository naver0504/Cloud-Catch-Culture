package com.example.eventservice.service.interaction;

import com.example.eventservice.domain.entity.interaction.Interaction;
import com.example.eventservice.domain.entity.interaction.LikeStar;
import com.example.eventservice.domain.repository.interaction.InteractionAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.eventservice.domain.entity.interaction.Interaction.createInteraction;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class InteractionService {

    private final InteractionAdapter interactionAdapter;

    @Transactional
    public void saveLikeStar(final int culturalEventId, final long userId, final LikeStar likeStar) {
        if (interactionAdapter.isLikeStarExist(culturalEventId, userId, likeStar)) {
            throw new IllegalStateException("Like already exists " + Thread.currentThread().getName());
        }
        final Interaction interaction = createInteraction(culturalEventId, userId, likeStar);
        interactionAdapter.save(interaction);
    }

    @Transactional
    public void deleteLikeStar(final int culturalEventId, final long userId, final LikeStar likeStar) {
        if (!interactionAdapter.isLikeStarExist(culturalEventId, userId, likeStar)) {
            throw new IllegalStateException("Like does not exist");
        }
        interactionAdapter.deleteInteraction(culturalEventId, userId, likeStar);
    }

    public boolean isLikedOrStar(int culturalEventId, long userId, final LikeStar likeStar) {
        return interactionAdapter.isLikeStarExist(culturalEventId, userId, likeStar);
    }
}
