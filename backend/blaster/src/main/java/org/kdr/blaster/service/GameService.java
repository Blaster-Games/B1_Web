package org.kdr.blaster.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.domain.game.*;
import org.kdr.blaster.dto.game.*;
import org.kdr.blaster.repository.game.*;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class GameService {

    private final MapRepository mapRepository;
    private final MatchRepository matchRepository;
    private final BuffRepository buffRepository;
    private final ThrowableObjectRepository throwableObjectRepository;
    private final WeaponRepository weaponRepository;
    private final BuffCountPerMatchRepository buffCountPerMatchRepository;
    private final ThrowableCountPerMatchRepository throwableCountPerMatchRepository;
    private final WeaponCountPerMatchRepository weaponCountPerMatchRepository;


    public Object testGet(StatisticsRequestDTO statisticsRequestDTO) {
        return mapRepository.calculateBuff(statisticsRequestDTO);
    }

    public Object saveMatchSummary(MatchResultDTO matchResultDTO) {
        GameMap gameMap = getMap(matchResultDTO);

        GameMatch matchTemp = GameMatch.builder()
                .gameMap(gameMap)
                .build();
        GameMatch gameMatch = matchRepository.save(matchTemp);

        for (BuffInfoDTO buffPurchase : matchResultDTO.getBuffPurchases()) {
            BuffCountPerMatch buffCountPerMatchTemp = BuffCountPerMatch.builder()
                    .count(buffPurchase.getPurchaseCount())
                    .match(gameMatch)
                    .buff(getBuff(buffPurchase.getBuffType()))
                    .build();
            buffCountPerMatchRepository.save(buffCountPerMatchTemp);
        }

        for (ThrowableInfoDTO throwablePurchase : matchResultDTO.getThrowablePurchases()) {
            ThrowableCountPerMatch throwableCountPerMatchTemp = ThrowableCountPerMatch.builder()
                    .count(throwablePurchase.getPurchaseCount())
                    .match(gameMatch)
                    .throwableObject(getThrowableObject(throwablePurchase.getThrowType()))
                    .build();
            throwableCountPerMatchRepository.save(throwableCountPerMatchTemp);
        }

        for (WeaponInfoDTO weaponInfoDTO : matchResultDTO.getWeaponPurchases()) {
            WeaponCountPerMatch weaponCountPerMatchTemp = WeaponCountPerMatch.builder()
                    .count(weaponInfoDTO.getPurchaseCount())
                    .match(gameMatch)
                    .weapon(getWeapon(weaponInfoDTO.getWeaponType()))
                    .build();
            weaponCountPerMatchRepository.save(weaponCountPerMatchTemp);
        }

        
        return Map.of("message", "success");
    }

    private GameMap getMap(MatchResultDTO matchResultDTO) {
        Optional<GameMap> optionalMap = mapRepository.findByName(matchResultDTO.getMapName());
        GameMap gameMap;
        if (optionalMap.isEmpty()) {
            GameMap gameMapTemp = GameMap.builder()
                    .name(matchResultDTO.getMapName())
                    .gameMod(matchResultDTO.getGameMode())
                    .build();
            gameMap = mapRepository.save(gameMapTemp);
        } else {
            gameMap = optionalMap.get();
        }
        return gameMap;
    }

    private Buff getBuff(String buffName) {
        Optional<Buff> optionalBuff = buffRepository.findByBuffType(buffName);
        Buff buff;
        if (optionalBuff.isEmpty()) {
            Buff buffTemp = Buff.builder().name(buffName).build();
            buff = buffRepository.save(buffTemp);
        } else {
            buff = optionalBuff.get();
        }
        return buff;
    }

    private ThrowableObject getThrowableObject(String throwableName) {
        Optional<ThrowableObject> optionalThrowableObject = throwableObjectRepository.findByThrowType(throwableName);
        ThrowableObject throwableObject;
        if (optionalThrowableObject.isEmpty()) {
            ThrowableObject throwableObjectTemp = ThrowableObject.builder().name(throwableName).build();
            throwableObject = throwableObjectRepository.save(throwableObjectTemp);
        } else {
            throwableObject = optionalThrowableObject.get();
        }
        return throwableObject;
    }

    private Weapon getWeapon(String weaponName) {
        Optional<Weapon> optionalWeapon = weaponRepository.findByWeaponType(weaponName);
        Weapon weapon;
        if (optionalWeapon.isEmpty()) {
            Weapon weaponTemp = Weapon.builder().name(weaponName).build();
            weapon = weaponRepository.save(weaponTemp);
        } else {
            weapon = optionalWeapon.get();
        }
        return weapon;
    }
}
