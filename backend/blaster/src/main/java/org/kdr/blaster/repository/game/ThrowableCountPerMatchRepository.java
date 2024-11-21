package org.kdr.blaster.repository.game;

import org.kdr.blaster.domain.game.ThrowableCountPerMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ThrowableCountPerMatchRepository extends JpaRepository<ThrowableCountPerMatch, Long> {
    List<ThrowableCountPerMatch> findByMatch_GameMap_NameAndMatch_PlayedAtBetween(String name, LocalDate startDate, LocalDate endDate);
}
