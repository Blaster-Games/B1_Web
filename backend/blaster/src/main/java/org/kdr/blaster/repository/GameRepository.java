package org.kdr.blaster.repository;

import org.kdr.blaster.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

}
