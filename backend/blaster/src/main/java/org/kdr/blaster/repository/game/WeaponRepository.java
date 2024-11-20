package org.kdr.blaster.repository.game;

import org.kdr.blaster.domain.game.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WeaponRepository extends JpaRepository<Weapon, Long> {
    @Query("select w from Weapon w where w.name = :name")
    Optional<Weapon> findByWeaponType(@Param("name") String name);
}
