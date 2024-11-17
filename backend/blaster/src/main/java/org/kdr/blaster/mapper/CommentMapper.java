package org.kdr.blaster.mapper;

import org.kdr.blaster.domain.board.Comment;
import org.kdr.blaster.dto.CommentDTO;
import org.kdr.blaster.util.DateTimeUtil;

public class CommentMapper {
    public static CommentDTO toCommentDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(DateTimeUtil.toString(comment.getCreatedAt()))
                .updatedAt(DateTimeUtil.toString(comment.getUpdatedAt()))
                .likeCount(comment.getLikeCount())
                .dislikeCount(comment.getDislikeCount())
                .memberId(comment.getMember().getId())
                .memberName(comment.getMember().getNickname())
                .build();
    }
}
