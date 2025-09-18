package com.example.GI.Backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Base64;

@Service
public class S3Service {


    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.folder-name}")
    private String folderName;

    public S3Service(@Value("${aws.s3.region}") String region) {
        this.s3Client = S3Client.builder()
                .credentialsProvider(DefaultCredentialsProvider.create()) // Uses IAM role credentials
                .region(software.amazon.awssdk.regions.Region.of(region))
                .build();
    }

    public String uploadFile(String filename, byte[] fileBytes) {
        String s3Key = folderName + "/" + filename;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromByteBuffer(ByteBuffer.wrap(fileBytes)));

        return s3Key; // Return relative path
    }

    public String getFileAsBase64(String relativePath) throws IOException, IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(relativePath)
                .build();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        s3Client.getObject(getObjectRequest).transferTo(outputStream);

        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

}
