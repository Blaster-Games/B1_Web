package org.kdr.blaster.repository.game;

import org.kdr.blaster.domain.game.WeaponCountPerMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WeaponCountPerMatchRepository extends JpaRepository<WeaponCountPerMatch, Long> {
    List<WeaponCountPerMatch> findByMatch_GameMap_NameAndMatch_PlayedAtBetween(String name, LocalDate startDate, LocalDate endDate);
}
