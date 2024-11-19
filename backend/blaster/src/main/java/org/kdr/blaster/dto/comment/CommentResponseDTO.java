package org.kdr.blaster.dto.comment;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDTO {

    private Long id;

    private String content;

    private String createdAt;

    private String updatedAt;

    private int likeCount;

    private int dislikeCount;

    private Long memberId;

    private String memberName;
}
