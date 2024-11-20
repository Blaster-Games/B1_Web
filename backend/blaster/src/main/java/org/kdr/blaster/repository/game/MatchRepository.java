package org.kdr.blaster.repository.game;

import org.kdr.blaster.domain.game.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
