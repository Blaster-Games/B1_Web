package org.kdr.blaster.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostReactionCountResponseDTO {
    private int likeCount;
    private int dislikeCount;
}
