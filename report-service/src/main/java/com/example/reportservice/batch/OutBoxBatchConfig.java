package com.example.reportservice.batch;

import com.example.reportservice.domain.adapter.outbox.OutBoxRepository;
import com.example.reportservice.domain.entity.outbox.OutBox;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@EnableBatchProcessing
@Configuration
@RequiredArgsConstructor
public class OutBoxBatchConfig {

    public static final String BEAN_PREFIX = "outBox";

    private final int CHUNK_SIZE = 100;
    private final JobRepository jobRepository;
    private final EntityManagerFactory entityManagerFactory;
    private final PlatformTransactionManager platformTransactionManager;

    private final OutBoxRepository outBoxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Bean(name = BEAN_PREFIX + "_job")
    public Job outBoxJob() {
        return new JobBuilder("outBoxJob", jobRepository)
                .start(outBoxStep())
                .build();
    }

    @Bean(name = BEAN_PREFIX + "_step")
    public Step outBoxStep() {
        return new StepBuilder("outBoxStep", jobRepository)
                .<OutBox, OutBox>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(outBoxReader())
                .writer(outBoxWriter())
                .build();
    }

    @Bean(name = BEAN_PREFIX + "_reader")
    public JpaPagingItemReader<OutBox> outBoxReader() {
        JpaPagingItemReader<OutBox> jpaPagingItemReader = new JpaPagingItemReader<>() {
            @Override
            public int getPage() {
                return 0;
            }
        };

        jpaPagingItemReader.setName(BEAN_PREFIX + "_reader");
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setPageSize(CHUNK_SIZE);
        jpaPagingItemReader.setQueryString("SELECT o FROM OutBox o order by o.id asc");

        return jpaPagingItemReader;
    }

    @Bean(name = BEAN_PREFIX + "_writer")
    public OutBoxBatchWriter outBoxWriter() {
        return new OutBoxBatchWriter(outBoxRepository, kafkaTemplate);
    }

}
