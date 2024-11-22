package org.kdr.blaster.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.domain.game.*;
import org.kdr.blaster.dto.game.*;
import org.kdr.blaster.repository.game.*;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

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

    /* getStats */
    public Object getStats(StatisticsRequestDTO statisticsRequestDTO) {
        List<RawDataDTO> buffData = mapRepository.calculateBuffStats(statisticsRequestDTO);
        List<RawDataDTO> throwableData = mapRepository.calculateThrowableStats(statisticsRequestDTO);
        List<RawDataDTO> weaponData = mapRepository.calculateWeaponStats(statisticsRequestDTO);


        LocalDate startDate = statisticsRequestDTO.getStartDate();
        LocalDate endDate = statisticsRequestDTO.getEndDate();
        int days = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;

        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd");

        ArrayList<String> labels = new ArrayList<>();
        HashMap<String, int[]> dataSetBuff = new LinkedHashMap<>();
        HashMap<String, int[]> dataSetThrowable = new LinkedHashMap<>();
        HashMap<String, int[]> dataSetWeapon = new LinkedHashMap<>();

        int i = 0;
        for (LocalDate d = startDate; d.isBefore(endDate.plusDays(1)); d = d.plusDays(1), i++) {
            final int index = i;
            final LocalDate day = d;
            labels.add(d.format(DATE_FORMATTER));
            buffData.forEach((s) -> {
                if (s.getPlayedAt().equals(day)) {
                    dataSetBuff.computeIfAbsent(s.getName(), k -> new int[days])[index] = s.getCount();
                }
            });
            throwableData.forEach((s) -> {
                if (s.getPlayedAt().equals(day)) {
                    dataSetThrowable.computeIfAbsent(s.getName(), k -> new int[days])[index] = s.getCount();
                }
            });
            weaponData.forEach((s) -> {
                if (s.getPlayedAt().equals(day)) {
                    dataSetWeapon.computeIfAbsent(s.getName(), k -> new int[days])[index] = s.getCount();
                }
            });
        }

        StatisticsResponseDTO statisticsResponseDTO = StatisticsResponseDTO.builder().labels(labels).build();
        for (String s : dataSetBuff.keySet()) {

            StatsDTO statsDTO = StatsDTO.builder()
                    .label(s)
                    .data(dataSetBuff.get(s))
                    .borderColor(generateRGB())
                    .backgroundColor(generateRGB())
                    .build();
            statisticsResponseDTO.getBuff().add(statsDTO);
        }

        for (String s : dataSetThrowable.keySet()) {
            StatsDTO statsDTO = StatsDTO.builder()
                    .label(s)
                    .data(dataSetThrowable.get(s))
                    .borderColor(generateRGB())
                    .backgroundColor(generateRGB())
                    .build();
            statisticsResponseDTO.getThrowable().add(statsDTO);
        }

        for (String s : dataSetWeapon.keySet()) {
            StatsDTO statsDTO = StatsDTO.builder()
                    .label(s)
                    .data(dataSetWeapon.get(s))
                    .borderColor(generateRGB())
                    .backgroundColor(generateRGB())
                    .build();
            statisticsResponseDTO.getWeapon().add(statsDTO);
        }

        return statisticsResponseDTO;
    }

    private String generateRGB() {
        Random random = new Random();
        return MessageFormat.format(
                "rgba({0}, {1}, {2}, {3})",
                random.nextInt(255),
                random.nextInt(255),
                random.nextInt(255),
                random.nextDouble() * 0.5 + 0.5);
    }

    /* saveMatchSummary */
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

    /* 맵 리스트 반환 */
    public Object getMaps() {
        List<GameMap> allMaps = mapRepository.findAll();
        List<String> mapList = allMaps.stream().map((m) -> m.getName()).toList();
        return mapList;
    }
}
