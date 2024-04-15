package com.example.reportservice.service.outbox;

import com.example.reportservice.entity.VisitAuthRequest;
import com.example.reportservice.entity.message.OutBox;
import com.example.reportservice.entity.message.EventType;
import com.example.reportservice.kafka.message.BaseMessage;
import com.example.reportservice.kafka.message.VisitAuthMessage;
import com.example.reportservice.repository.outbox.OutBoxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class OutBoxService {

    private final OutBoxRepository outBoxRepository;
    private final ObjectMapper objectMapper;


    public void createMessage(Object content)  {

        final EventType messageType = EventType.of(content.getClass());
        final VisitAuthMessage messageContent = VisitAuthMessage.from((VisitAuthRequest) content);

        final OutBox message = OutBox.builder()
                .eventType(messageType)
                .payload(getPayload(messageContent))
                .build();

        outBoxRepository.save(message);
    }


    private <T extends BaseMessage> String getPayload(final T content) {
        try {
            return objectMapper.writeValueAsString(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while serializing object to JSON");
        }
    }
}
