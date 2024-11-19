package org.kdr.blaster.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.kdr.blaster.domain.board.Category;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostPageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    @Builder.Default
    private String game = "blaster";

    @Builder.Default
    private Category category = Category.GENERAL;

    @Builder.Default
    private String sort = "createdAt";
}
