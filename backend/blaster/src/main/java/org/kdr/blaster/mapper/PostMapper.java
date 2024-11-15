package org.kdr.blaster.mapper;

import org.kdr.blaster.domain.board.Post;
import org.kdr.blaster.dto.PostListDTO;
import org.kdr.blaster.util.DateTimeUtil;

public class PostMapper {
    public static PostListDTO toPostListDTO(Post post) {
        return PostListDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(DateTimeUtil.toString(post.getCreatedAt()))
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .memberName(post.getMember().getNickname())
                .build();
    }
}
