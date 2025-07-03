# 1단계: 빌드
FROM node:18-alpine as build
WORKDIR /app
COPY frontend/blaster/package.json ./
COPY frontend/blaster/package-lock.json ./
RUN npm install
COPY frontend/blaster .
RUN npm run build

# 2단계: nginx에 정적 파일 복사
FROM nginx:stable-alpine
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
