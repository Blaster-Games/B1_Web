package org.kdr.blaster.dto.reaction;

import lombok.Getter;
import org.kdr.blaster.domain.board.Reaction;

@Getter
public class PostReactionChangeRequestDTO {
    private Long postId;
    private Reaction reaction;
}
