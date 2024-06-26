package com.example.reportservice.service.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.reportservice.common.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public String uploadFile(final MultipartFile file) {
        final String fileName = createImageName(file.getOriginalFilename());

        try(final InputStream inputStream = file.getInputStream()) {
            final PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, inputStream, createObjectMetadata(file));
            amazonS3.putObject(putObjectRequest);
            return amazonS3.getUrl(bucket, fileName).toString();
        }  catch (SdkClientException e) {
            log.error("uploadFile error: {}", e.getMessage());
            throw new IllegalStateException("Failed to upload file to S3");
        } catch (IOException e) {
            log.error("uploadFile error: {}", e.getMessage());
            throw new IllegalStateException("Failed to file input stream");
        }
    }

    private ObjectMetadata createObjectMetadata(final MultipartFile file) {
        final ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        return objectMetadata;
    }

    private String createImageName(String originalFileName) {
        return UUID.randomUUID() + "_" + originalFileName;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    @Async
    public void handleStoredFileUrlAfterRollback(final S3Event s3Event) {
        log.info("Handling stored file url after rollback");
        s3Event.getStoredFileUrl().stream()
                .map(ImageUtils::getImageFileUrlFromStoredFileUrl)
                .forEach(url -> amazonS3.deleteObject(bucket, url));
    }
}
