package org.kdr.blaster.repository.game;

import org.kdr.blaster.domain.game.BuffCountPerMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BuffCountPerMatchRepository extends JpaRepository<BuffCountPerMatch, Long> {
    List<BuffCountPerMatch> findByMatch_GameMap_NameAndMatch_PlayedAtBetween(String name, LocalDate startDate, LocalDate endDate);
}
