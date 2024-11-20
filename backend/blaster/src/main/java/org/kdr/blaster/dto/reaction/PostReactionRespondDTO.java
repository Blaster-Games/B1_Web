package org.kdr.blaster.dto.reaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kdr.blaster.domain.board.Reaction;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostReactionRespondDTO {
    private Reaction reaction;
}
