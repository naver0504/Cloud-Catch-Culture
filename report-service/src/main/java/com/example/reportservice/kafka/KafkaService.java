package com.example.reportservice.kafka;

import com.example.reportservice.entity.message.OutBox;
import com.example.reportservice.repository.outbox.OutBoxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OutBoxRepository messageRepository;

    public void sendMessage(final String topic, final OutBox outBox) {
        log.info("Sending message to kafka: {}", outBox.getPayload());
        kafkaTemplate.send(topic, outBox.getPayload());
        messageRepository.deleteById(outBox.getId());
    }


}