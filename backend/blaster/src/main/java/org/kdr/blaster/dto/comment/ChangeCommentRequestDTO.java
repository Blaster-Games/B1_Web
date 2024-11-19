package org.kdr.blaster.dto.comment;

import lombok.Getter;

@Getter
public class ChangeCommentRequestDTO {
    private Long commentId;
    private String content;
}
