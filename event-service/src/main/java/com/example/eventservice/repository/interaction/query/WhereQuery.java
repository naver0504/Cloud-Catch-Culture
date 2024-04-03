package com.example.eventservice.repository.interaction.query;

import com.example.eventservice.entity.interaction.LikeStar;
import com.querydsl.core.types.dsl.BooleanExpression;

import static com.example.eventservice.entity.interaction.QInteraction.interaction;

public class WhereQuery {

    public static BooleanExpression culturalEventIdEq(int culturalEventId) {
        return interaction.culturalEvent.id.eq(culturalEventId);
    }

    public static BooleanExpression userIdEq(long userId) {
        return interaction.userId.eq(userId);
    }

    public static BooleanExpression likeStarEq(LikeStar likeStar) {
        return interaction.likeStar.eq(likeStar);
    }


}
