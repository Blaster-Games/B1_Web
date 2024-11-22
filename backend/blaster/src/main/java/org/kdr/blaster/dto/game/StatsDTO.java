package org.kdr.blaster.dto.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatsDTO {
    private String label;
    private int[] data;
    private String borderColor;
    private String backgroundColor;
}
