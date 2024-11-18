package org.kdr.blaster.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AwsCredentialsConfig {
    public static void loadEnv() {
        Dotenv dotenv = Dotenv.configure()
                .directory("./") // `.env` 파일 위치 (프로젝트 루트 디렉토리)
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();

        System.setProperty("AWS_ACCESS_KEY_ID", dotenv.get("AWS_ACCESS_KEY_ID"));
        System.setProperty("AWS_SECRET_ACCESS_KEY", dotenv.get("AWS_SECRET_ACCESS_KEY"));
        System.setProperty("AWS_REGION", dotenv.get("AWS_REGION"));
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        log.info(dotenv.get("AWS_ACCESS_KEY_ID"));
        log.info(System.getProperties());
    }
}