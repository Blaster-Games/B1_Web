package org.kdr.blaster.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class UploadService {

    private final S3Client s3Client;

    public List<String> uploadImages(List<MultipartFile> files) {
        List<String> uploadedUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String uploadedUrl = uploadToS3(file);
                uploadedUrls.add(uploadedUrl);
            } catch (Exception e) {
                log.error("Failed to upload file: {}", file.getOriginalFilename(), e);
            }
        }
        return uploadedUrls;
    }

    private String uploadToS3(MultipartFile multipartFile) throws IOException {
        // 1. MultipartFile을 File로 변환
        File file = convertMultipartFileToFile(multipartFile);

        try {
            // 2. S3 버킷 이름과 파일 키 생성
            String bucketName = "kdy-storage-1"; // S3 버킷 이름 설정
            String key = "uploads/" + UUID.randomUUID() + "_" + file.getName();

            // 3. S3에 업로드 요청
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            // 4. S3 업로드 실행
            s3Client.putObject(putObjectRequest, file.toPath());

            // 5. 업로드된 파일 URL 반환
            return "https://" + bucketName + ".s3." + System.getProperty("AWS_REGION") + ".amazonaws.com/" + key;
        } finally {
            // 로컬 파일 삭제
            Files.deleteIfExists(file.toPath());
        }
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(UUID.randomUUID() + "_" + multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }
        return file;
    }
}
