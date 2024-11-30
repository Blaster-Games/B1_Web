package org.kdr.blaster.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.domain.game.*;
import org.kdr.blaster.domain.member.Member;
import org.kdr.blaster.dto.game.*;
import org.kdr.blaster.repository.GamePlayRecordRepository;
import org.kdr.blaster.repository.game.*;
import org.springframework.stereotype.Service;

import java.sql.Date;
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
    private final GamePlayRecordRepository gamePlayRecordRepository;
    private final MemberService memberService;

    /* getStats */
    public StatisticsResponseDTO getStats(StatisticsRequestDTO statisticsRequestDTO) {
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
            List<String> rgb = generateRGB();
            StatsDTO statsDTO = StatsDTO.builder()
                    .label(s)
                    .data(dataSetBuff.get(s))
                    .borderColor(rgb.get(1))
                    .backgroundColor(rgb.get(0))
                    .build();
            statisticsResponseDTO.getBuff().add(statsDTO);
        }

        for (String s : dataSetThrowable.keySet()) {
            List<String> rgb = generateRGB();
            StatsDTO statsDTO = StatsDTO.builder()
                    .label(s)
                    .data(dataSetThrowable.get(s))
                    .borderColor(rgb.get(1))
                    .backgroundColor(rgb.get(0))
                    .build();
            statisticsResponseDTO.getThrowable().add(statsDTO);
        }

        for (String s : dataSetWeapon.keySet()) {
            List<String> rgb = generateRGB();
            StatsDTO statsDTO = StatsDTO.builder()
                    .label(s)
                    .data(dataSetWeapon.get(s))
                    .borderColor(rgb.get(1))
                    .backgroundColor(rgb.get(0))
                    .build();
            statisticsResponseDTO.getWeapon().add(statsDTO);
        }

        return statisticsResponseDTO;
    }

    private List<String> generateRGB() {
        Random random = new Random();
        int r = random.nextInt(200);
        int g = random.nextInt(200);
        int b = random.nextInt(200);
        double a = random.nextDouble() * 0.5 + 0.5;
        return List.of(
                MessageFormat.format("rgba({0}, {1}, {2}, {3})", r, g, b, a),
                MessageFormat.format("rgba({0}, {1}, {2}, {3})", r + 55, g + 55, b + 55, a + 0.25)
        );
    }

    /* saveMatchSummary */
    public Object saveMatchSummary(MatchResultDTO matchResultDTO) {
        GameMap gameMap = getMap(matchResultDTO);

        GameMatch matchTemp = GameMatch.builder()
                .gameMap(gameMap)
                .build();
        GameMatch gameMatch = matchRepository.save(matchTemp);
        log.info(gameMatch);

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
        log.info(gameMap);
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
    public List<String> getMaps() {
        List<GameMap> allMaps = mapRepository.findAll();
        List<String> mapList = allMaps.stream().map((m) -> m.getName()).toList();
        return mapList;
    }

    /* 개인 게임 이용 시간 요청 */
    public Map getPlayTime(LocalDate start, LocalDate end) {
        Member member = memberService.getAuthenticatedMember();
        List<Object[]> queryResult = gamePlayRecordRepository.findTotalDurationByDate(member.getId(), start, end);

        ArrayList<LocalDate> labels = new ArrayList<>();

        int days = (int) ChronoUnit.DAYS.between(start, end) + 1;
        int [] data = new int[days];

        if (!queryResult.isEmpty()) {
            int i = 0, j = 0;
            for (LocalDate d = start; d.isBefore(end.plusDays(1)); d = d.plusDays(1), i++) {
                labels.add(d);
                if (j < queryResult.size() && d.equals(((Date) queryResult.get(j)[0]).toLocalDate())) {
                    data[i] = ((java.math.BigDecimal) (queryResult.get(j)[1])).intValue();
                    j++;
                }
            }
        }

        return Map.of("labels", labels, "data", data, "label", "게임 이용 시간 (분)", "y", "분");
    }

    public Object getGameVisitorsByDate(LocalDate start, LocalDate end) {
        List<Object[]> gameVisitorsByDate = gamePlayRecordRepository.getGameVisitorsByDate(start, end);

        ArrayList<LocalDate> labels = new ArrayList<>();

        int days = (int) ChronoUnit.DAYS.between(start, end) + 1;
        int [] data = new int[days];

        if (!gameVisitorsByDate.isEmpty()) {
            int i = 0, j = 0;
            for (LocalDate d = start; d.isBefore(end.plusDays(1)); d = d.plusDays(1), i++) {
                labels.add(d);
                if (j < gameVisitorsByDate.size() && d.equals(((Date) gameVisitorsByDate.get(j)[0]).toLocalDate())) {
                    data[i] = ((java.math.BigDecimal) (gameVisitorsByDate.get(j)[1])).intValue();
                    j++;
                }
            }
        }
        return Map.of("labels", labels, "data", data, "label", "접속자 수(명)", "y", "명");
    }
}
