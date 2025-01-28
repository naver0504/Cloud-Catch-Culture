package com.example.reportservice.kafka.producer;

import com.example.reportservice.batch.OutBoxBatchConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Component
@Slf4j
public class KafkaProducer {

    private final JobLauncher jobLauncher;
    private final OutBoxBatchConfig outBoxBatchConfig;

    @Scheduled(fixedRate = 10000) // 10 seconds
    public void sendOutBoxMessage() {
        log.info("Sending outbox messages to Kafka");
        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDateTime("time", LocalDateTime.now())
                .toJobParameters();

        try {
            jobLauncher.run(outBoxBatchConfig.outBoxJob(), jobParameters);
        } catch (Exception e) {
            log.error("Error while spring outbox batch", e);
        }
    }
}
