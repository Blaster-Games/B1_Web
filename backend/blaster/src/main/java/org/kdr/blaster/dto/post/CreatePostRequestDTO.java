package org.kdr.blaster.dto.post;

import lombok.Data;
import org.kdr.blaster.domain.board.Category;

@Data
public class CreatePostRequestDTO {

    private Category category;

    private String title;

    private String content;
}
