package com.example.eventservice.domain.adapter.interaction.query;

import com.example.eventservice.domain.entity.interaction.LikeStar;
import com.querydsl.core.types.dsl.BooleanExpression;

import static com.example.eventservice.domain.entity.interaction.QInteraction.interaction;

public class WhereQuery {

    public static BooleanExpression interactionCulturalEventIdEq(int culturalEventId) {
        return interaction.culturalEvent.id.eq(culturalEventId);
    }

    public static BooleanExpression interactionUserIdEq(long userId) {
        return interaction.userId.eq(userId);
    }

    public static BooleanExpression likeStarEq(LikeStar likeStar) {
        return interaction.likeStar.eq(likeStar);
    }


}
