package org.kdr.blaster.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.dto.game.MatchResultDTO;
import org.kdr.blaster.dto.game.StatisticsRequestDTO;
import org.kdr.blaster.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game")
@Log4j2
public class GameController {

    private final GameService gameService;

    @PostMapping("/match/result")
    public ResponseEntity<?> saveMatchSummary(@RequestBody MatchResultDTO matchResultDTO) {
        return ResponseEntity.ok(gameService.saveMatchSummary(matchResultDTO));
    }

    @PostMapping("/stats")
    public ResponseEntity<?> getStats(@RequestBody StatisticsRequestDTO statisticsRequestDTO) {
        log.info(statisticsRequestDTO);
        return ResponseEntity.ok(gameService.getStats(statisticsRequestDTO));
    }

    @GetMapping("/maps")
    public ResponseEntity<?> getMaps() {
        return ResponseEntity.ok(gameService.getMaps());
    }
}
