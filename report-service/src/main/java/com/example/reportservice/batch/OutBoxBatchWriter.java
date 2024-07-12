package com.example.reportservice.batch;

import com.example.reportservice.domain.adapter.outbox.OutBoxRepository;
import com.example.reportservice.domain.entity.outbox.OutBox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@RequiredArgsConstructor
public class OutBoxBatchWriter implements ItemWriter<OutBox> {

    private final OutBoxRepository outBoxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void write(Chunk<? extends OutBox> chunk) {
        for (OutBox outBox : chunk.getItems()) {
            kafkaTemplate.send(outBox.getEventType().getTopic(), outBox.getPayload());
            log.info("Sent message to Kafka: {}", outBox.getPayload());
        }
        outBoxRepository.deleteAllByIdInBatch(chunk.getItems().stream().map(OutBox::getId).toList());
    }
}
