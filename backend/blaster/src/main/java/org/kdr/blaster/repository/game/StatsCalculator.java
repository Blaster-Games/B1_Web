package org.kdr.blaster.repository.game;

import org.kdr.blaster.dto.game.StatisticsRequestDTO;
import org.kdr.blaster.dto.game.RawDataDTO;

import java.util.List;

public interface StatsCalculator {
    List<RawDataDTO> calculateBuffStats(StatisticsRequestDTO statisticsRequestDTO);
    List<RawDataDTO> calculateThrowableStats(StatisticsRequestDTO statisticsRequestDTO);
    List<RawDataDTO> calculateWeaponStats(StatisticsRequestDTO statisticsRequestDTO);
}
