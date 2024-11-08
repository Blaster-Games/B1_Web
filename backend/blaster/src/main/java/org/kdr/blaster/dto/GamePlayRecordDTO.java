package org.kdr.blaster.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kdr.blaster.domain.GamePlayRecord;
import org.kdr.blaster.domain.member.Member;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GamePlayRecordDTO {

    private LocalDateTime loginTime;

    private LocalDateTime logoutTime;

    private Long duration;

    public static GamePlayRecordDTO entityToDTO(GamePlayRecord gamePlayRecord) {
        return GamePlayRecordDTO.builder()
                .loginTime(gamePlayRecord.getLoginTime())
                .logoutTime(gamePlayRecord.getLogoutTime())
                .duration(gamePlayRecord.getDuration())
                .build();
    }

}
