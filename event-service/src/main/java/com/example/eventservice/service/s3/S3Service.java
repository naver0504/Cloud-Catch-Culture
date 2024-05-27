package com.example.eventservice.service.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
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
import java.util.List;
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

    private void deleteFile(final String fileUrlForDelete) {
        amazonS3.deleteObject(bucket, fileUrlForDelete);
    }

    private String createImageName(String originalFileName) {
        return UUID.randomUUID() + "_" + originalFileName;
    }


    public String getImageFileUrlFromStoredFileUrl(final String storedFileUrl) {
        return storedFileUrl.split("/")[storedFileUrl.split("/").length - 1];
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handleDeleteFileEvent(final S3EventForDelete eventForDelete) {
        log.info("handleDeleteFileEvent");
        final List<String> storedImageUrlForDelete = eventForDelete.storedImageUrlForDelete();
        storedImageUrlForDelete.stream()
                .map(this::getImageFileUrlFromStoredFileUrl)
                .forEach(this::deleteFile);

    }

}
