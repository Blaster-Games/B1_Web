package org.kdr.blaster.repository;

import org.kdr.blaster.domain.member.GamePlayRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamePlayRecordRepository extends JpaRepository<GamePlayRecord, Long> {
}
