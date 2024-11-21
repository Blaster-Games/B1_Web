package org.kdr.blaster.repository.game;

import org.kdr.blaster.dto.game.StatisticsRequestDTO;
import org.kdr.blaster.dto.game.StatisticsResultDTO;

import java.util.List;

public interface StatsCalculator {
    List<StatisticsResultDTO> calculateBuff(StatisticsRequestDTO statisticsRequestDTO);
}
