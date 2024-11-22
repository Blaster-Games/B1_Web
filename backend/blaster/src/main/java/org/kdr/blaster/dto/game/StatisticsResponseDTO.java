package org.kdr.blaster.dto.game;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StatisticsResponseDTO {
    private List<String> labels;
    @Builder.Default
    private List<StatsDTO> buff = new ArrayList<>();
    @Builder.Default
    private List<StatsDTO> throwable = new ArrayList<>();
    @Builder.Default
    private List<StatsDTO> weapon = new ArrayList<>();
}
