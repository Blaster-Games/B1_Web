package org.kdr.blaster.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
