package org.kdr.blaster;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {
    public static void main(String[] args) {
        String fileName = "data.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // CSV 헤더 작성
            writer.write("category,comment_count,content,created_at,deleted,dislike_count,like_count,title,updated_at,view_count,game_id,author_id");
            writer.newLine();

            // 데이터 작성
            for (int i = 1; i <= 1_000_000; i++) {
                writer.write("GENERAL" + "," + 0 + "," + "<p>content" + i + "</p>" + "," + "2024-11-" + (i % 30 + 1) + " 09:58:26.192 +0900" + "," +
                        0 + "," + 0 + "," + 1 + "," + "title" + i + "," +
                        "2024-11-" + (i % 30 + 1) + " 09:58:26.192 +0900" + "," + (i % 50) + "," + 1 + "," + (i % 111 + 1));
                writer.newLine();

                // 진행 상태 출력 (선택 사항)
                if (i % 100000 == 0) {
                    System.out.println("Generated " + i + " rows...");
                }
            }

            System.out.println("CSV 파일 생성 완료: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

