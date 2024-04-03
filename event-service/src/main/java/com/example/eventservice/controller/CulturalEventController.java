package com.example.eventservice.controller;

import com.example.eventservice.common.type.SortType;
import com.example.eventservice.dto.EventResponseDTO;
import com.example.eventservice.entity.event.Category;
import com.example.eventservice.entity.interaction.LikeStar;
import com.example.eventservice.service.event.CulturalEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cultural-event")
@RequiredArgsConstructor
public class CulturalEventController {

    private final CulturalEventService culturalEventService;

    @GetMapping
    public ResponseEntity<Page<EventResponseDTO>> getCulturalEventList(final @RequestParam(required = false) String keyword,
                                                                       final @RequestParam(required = false, name = "category") List<Category> categoryList,
                                                                       final @RequestParam(required = false, defaultValue = "0") int offset,
                                                                       final @RequestParam(required = false, defaultValue = "RECENT") SortType sortType) {
        return ResponseEntity.ok(culturalEventService.getCulturalEventList(keyword, categoryList, offset, sortType));
    }

    @PostMapping("{culturalEventId}/likestar")
    public ResponseEntity<Void> likeCulturalEvent(final @PathVariable int culturalEventId,
                                                  final @RequestHeader("userId") long userId,
                                                  final @RequestParam("interaction") LikeStar likeStar) {
        culturalEventService.createInteraction(culturalEventId, userId, likeStar);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{culturalEventId}/likestar")
    public ResponseEntity<Void> cancelLikeCulturalEvent(final @PathVariable int culturalEventId,
                                                        final @RequestParam("interaction") LikeStar likeStar,
                                                        final @RequestHeader("userId") long userId) {
        culturalEventService.cancelInteraction(culturalEventId, userId, likeStar);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}