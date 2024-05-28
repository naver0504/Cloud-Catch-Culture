package com.example.reportservice.service.outbox;

import com.example.reportservice.domain.entity.BaseEntity;
import com.example.reportservice.domain.entity.outbox.OutBox;
import com.example.reportservice.kafka.message.BaseMessage;
import com.example.reportservice.domain.adapter.outbox.OutBoxRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class OutBoxService {

    private final OutBoxRepository outBoxRepository;
    private final ObjectMapper objectMapper;


    public <T extends BaseEntity> void createMessage(final T content) {

        final BaseMessage baseMessage = content.toBaseMessage();
        final String payload = baseMessage.getPayload(objectMapper);

        final OutBox outbox = OutBox.builder()
                .eventType(baseMessage.getEventType())
                .payload(payload)
                .build();

        outBoxRepository.save(outbox);
    }


}
