package com.example.eventservice.service.interaction;

import com.example.eventservice.entity.interaction.Interaction;
import com.example.eventservice.entity.interaction.LikeStar;
import com.example.eventservice.repository.interaction.InteractionQueryRepository;
import com.example.eventservice.repository.interaction.InteractionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.eventservice.entity.interaction.Interaction.createInteraction;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class InteractionService {

    private final InteractionRepository interactionRepository;
    private final InteractionQueryRepository interactionQueryRepository;

    @Transactional
    public void saveLikeStar(final int culturalEventId, final long userId, final LikeStar likeStar) {
        if (interactionQueryRepository.isLikeStarExist(culturalEventId, userId, likeStar)) {
            throw new IllegalStateException("Like already exists " + Thread.currentThread().getName());
        }
        final Interaction interaction = createInteraction(culturalEventId, userId, likeStar);
        interactionRepository.save(interaction);
    }

    @Transactional
    public void deleteLikeStar(final int culturalEventId, final long userId, final LikeStar likeStar) {
        if (!interactionQueryRepository.isLikeStarExist(culturalEventId, userId, likeStar)) {
            throw new IllegalStateException("Like does not exist");
        }
        interactionRepository.deleteInteraction(culturalEventId, userId, likeStar);
    }

    public boolean isLikedOrStar(int culturalEventId, long userId, final LikeStar likeStar) {
        return interactionQueryRepository.isLikeStarExist(culturalEventId, userId, likeStar);
    }
}
