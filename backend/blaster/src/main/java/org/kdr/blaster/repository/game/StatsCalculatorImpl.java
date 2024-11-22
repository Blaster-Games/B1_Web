package org.kdr.blaster.repository.game;

import com.querydsl.core.types.Projections;
import org.kdr.blaster.domain.game.*;
import org.kdr.blaster.dto.game.StatisticsRequestDTO;
import org.kdr.blaster.dto.game.RawDataDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class StatsCalculatorImpl extends QuerydslRepositorySupport implements StatsCalculator {

    public StatsCalculatorImpl() {
        super(GameMap.class);
    }

    @Override
    public List<RawDataDTO> calculateBuffStats(StatisticsRequestDTO statisticsRequestDTO) {
        QGameMap map = QGameMap.gameMap;
        QGameMatch match = QGameMatch.gameMatch;
        QBuffCountPerMatch buffCountPerMatch = QBuffCountPerMatch.buffCountPerMatch;
        QBuff buff = QBuff.buff;

        return from(map)
                .select(Projections.constructor(
                        RawDataDTO.class,
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

    @Override
    public List<RawDataDTO> calculateThrowableStats(StatisticsRequestDTO statisticsRequestDTO) {
        QGameMap map = QGameMap.gameMap;
        QGameMatch match = QGameMatch.gameMatch;
        QThrowableCountPerMatch throwableCountPerMatch = QThrowableCountPerMatch.throwableCountPerMatch;
        QThrowableObject throwableObject = QThrowableObject.throwableObject;

        return from(map)
                .select(Projections.constructor(
                        RawDataDTO.class,
                        throwableObject.name,
                        match.playedAt,
                        throwableCountPerMatch.count.sum()
                ))
                .innerJoin(match).on(match.gameMap.eq(map))
                .innerJoin(throwableCountPerMatch).on(throwableCountPerMatch.match.eq(match))
                .innerJoin(throwableObject).on(throwableCountPerMatch.throwableObject.eq(throwableObject))
                .where(map.name.eq(statisticsRequestDTO.getMapName())
                        .and(match.playedAt.between(statisticsRequestDTO.getStartDate(), statisticsRequestDTO.getEndDate())))
                .groupBy(throwableObject.name, match.playedAt)
                .fetch();
    }

    @Override
    public List<RawDataDTO> calculateWeaponStats(StatisticsRequestDTO statisticsRequestDTO) {
        QGameMap map = QGameMap.gameMap;
        QGameMatch match = QGameMatch.gameMatch;
        QWeaponCountPerMatch weaponCountPerMatch = QWeaponCountPerMatch.weaponCountPerMatch;
        QWeapon weapon = QWeapon.weapon;

        return from(map)
                .select(Projections.constructor(
                        RawDataDTO.class,
                        weapon.name,
                        match.playedAt,
                        weaponCountPerMatch.count.sum()
                ))
                .innerJoin(match).on(match.gameMap.eq(map))
                .innerJoin(weaponCountPerMatch).on(weaponCountPerMatch.match.eq(match))
                .innerJoin(weapon).on(weaponCountPerMatch.weapon.eq(weapon))
                .where(map.name.eq(statisticsRequestDTO.getMapName())
                        .and(match.playedAt.between(statisticsRequestDTO.getStartDate(), statisticsRequestDTO.getEndDate())))
                .groupBy(weapon.name, match.playedAt)
                .fetch();
    }
}
