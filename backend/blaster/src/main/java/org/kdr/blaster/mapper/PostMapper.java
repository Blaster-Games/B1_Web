package org.kdr.blaster.mapper;

import org.kdr.blaster.domain.board.Post;
import org.kdr.blaster.dto.post.PostDTO;
import org.kdr.blaster.dto.post.PostListDTO;
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

    public static PostDTO toPostDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(DateTimeUtil.toString(post.getCreatedAt()))
                .updatedAt(DateTimeUtil.toString(post.getUpdatedAt()))
                .category(post.getCategory().name())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .dislikeCount(post.getDislikeCount())
                .commentCount(post.getCommentCount())
                .memberId(post.getMember().getId())
                .memberName(post.getMember().getNickname())
                .build();
    }
}
