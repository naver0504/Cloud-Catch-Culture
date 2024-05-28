package com.example.reportservice.kafka.producer;

import com.example.reportservice.domain.entity.outbox.EventType;
import com.example.reportservice.domain.entity.outbox.OutBox;
import com.example.reportservice.kafka.KafkaService;
import com.example.reportservice.domain.adapter.outbox.OutBoxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class KafkaProducer {

    private final KafkaService kafkaService;
    private final OutBoxRepository outBoxRepository;

    @Scheduled(fixedRate = 10000) // 10 seconds
    public void sendOutBoxMessage() {
        final List<OutBox> outBoxes = outBoxRepository.findAll();
        for (final OutBox message : outBoxes) {
            final EventType eventType = message.getEventType();
            kafkaService.sendMessage(eventType.getTopic(), message);
        }
    }
}
