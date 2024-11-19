package org.kdr.blaster.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostListDTO {

    private long id;

    private String title;

    private String content;

    private String createdAt;

    private long viewCount;

    private int likeCount;

    private int commentCount;

    private String memberName;
}
