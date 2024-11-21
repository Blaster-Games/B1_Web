package org.kdr.blaster.repository.game;

import org.kdr.blaster.domain.game.GameMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MapRepository extends JpaRepository<GameMap, Long>, StatsCalculator {
    Optional<GameMap> findByName(String name);
}
