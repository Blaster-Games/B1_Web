package org.kdr.blaster.repository.game;

import org.kdr.blaster.domain.game.ThrowableObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ThrowableObjectRepository extends JpaRepository<ThrowableObject, Long> {
    @Query("select t from ThrowableObject t where t.name = :name")
    Optional<ThrowableObject> findByThrowType(@Param("name") String name);
}
