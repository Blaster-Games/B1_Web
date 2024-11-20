package org.kdr.blaster.repository.game;

import org.kdr.blaster.domain.game.Buff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BuffRepository extends JpaRepository<Buff, Long> {
    @Query("select b from Buff b where b.name = :name")
    Optional<Buff> findByBuffType(@Param("name") String name);
}
