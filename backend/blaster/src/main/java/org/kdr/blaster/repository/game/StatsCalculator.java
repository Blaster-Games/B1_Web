package org.kdr.blaster.repository.game;

import com.querydsl.core.Tuple;
import org.kdr.blaster.domain.member.GamePlayRecord;
import org.kdr.blaster.domain.member.Member;
import org.kdr.blaster.dto.game.StatisticsRequestDTO;
import org.kdr.blaster.dto.game.RawDataDTO;

import java.time.LocalDate;
import java.util.List;

public interface StatsCalculator {
    List<RawDataDTO> calculateBuffStats(StatisticsRequestDTO statisticsRequestDTO);
    List<RawDataDTO> calculateThrowableStats(StatisticsRequestDTO statisticsRequestDTO);
    List<RawDataDTO> calculateWeaponStats(StatisticsRequestDTO statisticsRequestDTO);
//    List<Tuple> calculatePlayTime(Member member, LocalDate start, LocalDate end);
}
