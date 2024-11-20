package org.kdr.blaster.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.dto.game.MatchResultDTO;
import org.kdr.blaster.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
