# 1단계: 빌드 단계 (Gradle)
FROM gradle:8.4-jdk17 AS build

# 빌드에 필요한 파일만 먼저 복사 (의존성 캐싱 용도)
COPY backend/blaster/build.gradle backend/blaster/settings.gradle ./
COPY backend/blaster/gradle ./gradle

# 의존성 미리 다운로드
RUN gradle dependencies

# 전체 프로젝트 복사 후 빌드
COPY backend/blaster/. .

# 실행 가능한 JAR 생성
RUN gradle bootJar

# 2단계: 실행 단계 (경량 JDK 이미지)
FROM eclipse-temurin:17-jdk-alpine

# 앱 디렉토리 생성
WORKDIR /app

# 빌드한 JAR만 복사
COPY --from=build /home/gradle/build/libs/*.jar app.jar

EXPOSE 8080

# 앱 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
