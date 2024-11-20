package org.kdr.blaster.repository.game;

import org.kdr.blaster.domain.game.WeaponCountPerMatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponCountPerMatchRepository extends JpaRepository<WeaponCountPerMatch, Long> {
}
