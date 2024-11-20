package org.kdr.blaster.repository.game;

import org.kdr.blaster.domain.game.ThrowableCountPerMatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThrowableCountPerMatchRepository extends JpaRepository<ThrowableCountPerMatch, Long> {
}
