package org.kdr.blaster.dto.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RawDataDTO {
    private String name;
    private LocalDate playedAt;
    private int count;
}
