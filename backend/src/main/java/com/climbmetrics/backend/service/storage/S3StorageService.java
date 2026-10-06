package com.climbmetrics.backend.service.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

public class S3StorageService implements StorageService {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    public S3StorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String store(MultipartFile file) {
        // upload to S3
        return "";
    }

    @Override
    public Resource loadAsResource(String filename) {
        // retrieve from S3
        return null;
    }

    @Override
    public void delete(String filename) {
        // delete from S3
    }
}
