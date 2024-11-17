package org.kdr.blaster.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private Long id;

    private String title;

    private String content;

    private String createdAt;

    private String updatedAt;

    private String category;

    private long viewCount;

    private int likeCount;

    private int dislikeCount;

    private int commentCount;

    private Long memberId;

    private String memberName;

    @Builder.Default
    private List<CommentDTO> comments = new ArrayList<>();
}
