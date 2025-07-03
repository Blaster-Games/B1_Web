package org.kdr.blaster.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Log4j2
public class AwsS3Config {
    @Bean
    public S3Client s3Client() {
        // AWS 자격 증명을 환경 변수에서 읽어옵니다.
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                System.getenv("AWS_ACCESS_KEY_ID"), // 환경 변수에서 Access Key 가져오기
                System.getenv("AWS_SECRET_ACCESS_KEY") // 환경 변수에서 Secret Key 가져오기
                // 로컬에서는 System.getProperty - Java 애플리케이션 내에서 설정된 시스템 속성을 가져옵니다.
                // 운영 환경에서는 System.getenv() - OS 레벨의 환경 변수를 가져옵니다. OS에 설정된 환경 변수만 읽습니다.
                // .env 파일을 사용한 경우 Dotenv 라이브러리가 환경 변수가 아닌 시스템 속성으로 값을 설정했기 때문
                // 로컬과 운영 환경 모두를 지원하려면 getProperty와 getenv를 조합한 메서드를 사용

        );

        return S3Client.builder()
                .region(Region.of(System.getenv("AWS_REGION"))) // 리전 설정
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }
}

/*
5. 최적의 구현
운영 환경과 로컬 개발 환경의 설정 방식을 모두 수용하려면, getProperty와 getenv를 함께 사용하는 구조가 좋습니다:
public static String getEnvOrProperty(String key) {
    // 먼저 시스템 속성에서 값을 가져오고, 없으면 환경 변수에서 가져옴
    return System.getProperty(key, System.getenv(key));
}
위 메서드를 사용하면 다음처럼 호출할 수 있습니다:

java
코드 복사
AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
    getEnvOrProperty("AWS_ACCESS_KEY_ID"),
    getEnvOrProperty("AWS_SECRET_ACCESS_KEY")
);
*/
