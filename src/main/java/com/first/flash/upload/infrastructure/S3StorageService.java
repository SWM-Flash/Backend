package com.first.flash.upload.infrastructure;

import com.first.flash.upload.application.StorageService;
import com.first.flash.upload.exception.exceptions.FileStoreFailedException;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3StorageService implements StorageService {

    private final S3Client s3Client;

    @Value("${aws.s3.input-bucket.image}")
    private String imageBucketName;

    @Value("${aws.s3.input-bucket.video}")
    private String videoInputBucketName;

    @Value("${aws.cdn.domain}")
    private String cdnDomain;

    @Override
    public String storageImage(final MultipartFile imageFile) {

        String fileName = UUID.randomUUID() + ".webp";
        storageFile(imageFile, fileName, imageBucketName);
        return cdnDomain + "/" + fileName;
    }

    @Override
    public String storageVideo(final MultipartFile videoFile) {
        String fileName = UUID.randomUUID() + ".mp4";
        storageFile(videoFile, fileName, videoInputBucketName);
        return "s3://" + videoInputBucketName + "/" + fileName;
    }

    private void storageFile(final MultipartFile file, final String fileName,
        final String bucketName) {
        try {
            s3Client.putObject(PutObjectRequest.builder()
                                               .bucket(bucketName)
                                               .key(fileName)
                                               .build(),
                RequestBody.fromBytes(file.getBytes()));
        } catch (S3Exception | IOException e) {
            throw new FileStoreFailedException();
        }
    }
}
