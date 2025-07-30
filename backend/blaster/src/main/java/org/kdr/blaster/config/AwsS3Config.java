package org.kdr.blaster.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class AwsS3Config {

    private final AwsProperties awsProperties;

    @Bean
    public S3Client s3Client() {
        // AWS 자격 증명을 환경 변수에서 읽어오기
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                awsProperties.getAccessKeyId(), // 환경 변수에서 Access Key 가져오기
                awsProperties.getSecretAccessKey() // 환경 변수에서 Secret Key 가져오기
        );

        return S3Client.builder()
                .region(Region.of(awsProperties.getRegion())) // 리전 설정
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }
}
