package com.example.reportservice.batch;

import com.example.reportservice.domain.adapter.outbox.OutBoxRepository;
import com.example.reportservice.domain.entity.outbox.EventType;
import com.example.reportservice.domain.entity.outbox.OutBox;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(BatchTestConfiguration.class)
@SpringBatchTest
class OutBoxBatchConfigTest {
    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    OutBoxRepository outBoxRepository;
    @Autowired
    JobRepositoryTestUtils jobRepositoryTestUtils;

    @AfterEach
    void tearDown() {
        jobRepositoryTestUtils.removeJobExecutions();
        outBoxRepository.deleteAll();
    }


    @Test
    void test() throws Exception {
        // given
        int MAX_SIZE = 150;
        for (int i = 0; i < MAX_SIZE; i++) {
            outBoxRepository.save(OutBox.builder().eventType(EventType.TEST).payload("test" + i).build());
        }

        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDateTime("date", LocalDateTime.now())
                .toJobParameters();

        // when
        jobLauncherTestUtils.launchJob(jobParameters);
        // then
        assertEquals(0, outBoxRepository.findAll().size());
    }
}