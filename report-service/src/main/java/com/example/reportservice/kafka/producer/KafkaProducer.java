package com.example.reportservice.kafka.producer;

import com.example.reportservice.entity.outbox.EventType;
import com.example.reportservice.entity.outbox.OutBox;
import com.example.reportservice.kafka.KafkaService;
import com.example.reportservice.repository.outbox.OutBoxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.reportservice.kafka.KafkaConstant.CREATE_EVENT_REPORT;
import static com.example.reportservice.kafka.KafkaConstant.CREATE_VISIT_AUTH;

@RequiredArgsConstructor
@Component
@Slf4j
public class KafkaProducer {

    private final KafkaService kafkaService;
    private final OutBoxRepository outBoxRepository;

    @Scheduled(fixedRate = 5000) // 5 seconds
    public void sendOutBoxMessage() {

        log.info("Sending outbox messages to kafka");
        final List<OutBox> outBoxes = outBoxRepository.findAll();

        for (final OutBox message : outBoxes) {
            final EventType eventType = message.getEventType();
            switch (eventType.getKafkaTopic()) {
                case CREATE_VISIT_AUTH -> kafkaService.sendMessage(CREATE_VISIT_AUTH, message);
                case CREATE_EVENT_REPORT -> kafkaService.sendMessage(CREATE_EVENT_REPORT, message);
            }
        }
    }
}
