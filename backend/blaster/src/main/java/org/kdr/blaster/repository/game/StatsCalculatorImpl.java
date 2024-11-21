package org.kdr.blaster.repository.game;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.kdr.blaster.domain.game.*;
import org.kdr.blaster.dto.game.StatisticsRequestDTO;
import org.kdr.blaster.dto.game.StatisticsResultDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class StatsCalculatorImpl extends QuerydslRepositorySupport implements StatsCalculator {

    public StatsCalculatorImpl() {
        super(GameMap.class);
    }

    @Override
    public List<StatisticsResultDTO> calculateBuff(StatisticsRequestDTO statisticsRequestDTO) {
        QGameMap map = QGameMap.gameMap;
        QGameMatch match = QGameMatch.gameMatch;
        QBuffCountPerMatch buffCountPerMatch = QBuffCountPerMatch.buffCountPerMatch;
        QBuff buff = QBuff.buff;

        return from(map)
                .select(Projections.constructor(
                        StatisticsResultDTO.class,
                        buff.name,
                        match.playedAt,
                        buffCountPerMatch.count.sum()
                ))
                .innerJoin(match).on(match.gameMap.eq(map))
                .innerJoin(buffCountPerMatch).on(buffCountPerMatch.match.eq(match))
                .innerJoin(buff).on(buffCountPerMatch.buff.eq(buff))
                .where(map.name.eq(statisticsRequestDTO.getMapName())
                        .and(match.playedAt.between(statisticsRequestDTO.getStartDate(), statisticsRequestDTO.getEndDate())))
                .groupBy(buff.name, match.playedAt)
                .fetch();
    }
}
