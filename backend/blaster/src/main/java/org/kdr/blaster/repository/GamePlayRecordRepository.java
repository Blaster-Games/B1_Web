package org.kdr.blaster.repository;

import org.kdr.blaster.domain.member.GamePlayRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface GamePlayRecordRepository extends JpaRepository<GamePlayRecord, Long> {
    @Query(value = """
    SELECT date, SUM(duration) AS total_duration
    FROM (
        SELECT 
            DATE(login_time) AS date,
            SUM(
                CASE
                    WHEN DATE(login_time) = DATE(logout_time) THEN duration
                    WHEN DATE(login_time) != DATE(logout_time) THEN 
                        TIMESTAMPDIFF(minute, login_time, DATE_ADD(DATE(login_time), INTERVAL 1 DAY))
                END
            ) AS duration
        FROM game_play_record gpr
        WHERE gpr.member_id = :memberId 
          AND DATE(login_time) BETWEEN :startDate AND :endDate
        GROUP BY DATE(login_time)

        UNION ALL

        SELECT 
            DATE(logout_time) AS date,
            SUM(
                CASE
                    WHEN DATE(login_time) != DATE(logout_time) THEN 
                        TIMESTAMPDIFF(minute, DATE(logout_time), logout_time)
                    ELSE 0
                END
            ) AS duration
        FROM game_play_record gpr
        WHERE DATE(login_time) != DATE(logout_time) 
          AND gpr.member_id = :memberId
          AND DATE(login_time) BETWEEN :startDate AND :endDate
        GROUP BY DATE(logout_time)
    ) AS split_durations
    GROUP BY date
    ORDER BY date ASC
    """, nativeQuery = true)
    List<Object[]> findTotalDurationByDate(
            @Param("memberId") Long memberId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query(value = """
        SELECT date, SUM(number) AS total_number
        FROM (
            SELECT DATE(gpr.login_time) AS date, COUNT(DISTINCT gpr.member_id) AS number
            FROM game_play_record gpr
            WHERE DATE(gpr.login_time) BETWEEN :startDate AND :endDate
            GROUP BY DATE(gpr.login_time)
            
            UNION ALL
            
            SELECT DATE(gpr.logout_time) AS date, COUNT(DISTINCT gpr.member_id) AS number
            FROM game_play_record gpr
            WHERE DATE(gpr.login_time) != DATE(gpr.logout_time) 
              AND DATE(gpr.logout_time) BETWEEN :startDate AND :endDate
            GROUP BY DATE(gpr.logout_time)
        ) AS temp
        GROUP BY date
    """, nativeQuery = true)
    List<Object[]> getGameVisitorsByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
