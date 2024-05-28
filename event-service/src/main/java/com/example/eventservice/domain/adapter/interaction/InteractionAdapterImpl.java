package com.example.eventservice.domain.adapter.interaction;

import com.example.eventservice.domain.entity.interaction.Interaction;
import com.example.eventservice.domain.entity.interaction.LikeStar;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class InteractionAdapterImpl implements InteractionAdapter{
    private final InteractionQueryRepository interactionQueryRepository;
    private final InteractionRepository interactionRepository;

    @Override
    public void deleteInteraction(int culturalEventId, long userId, LikeStar likeStar) {
        interactionRepository.deleteInteraction(culturalEventId, userId, likeStar);
    }

    @Override
    public boolean isLikeStarExist(int culturalEventId, long userId, LikeStar likeStar) {
        return interactionQueryRepository.isLikeStarExist(culturalEventId, userId, likeStar);
    }

    @Override
    public Optional<Interaction> findById(Integer id) {
        return interactionRepository.findById(id);
    }

    @Override
    public Interaction save(Interaction interaction) {
        return interactionRepository.save(interaction);
    }

    @Override
    public void deleteById(Integer id) {
        interactionRepository.deleteById(id);
    }
}
