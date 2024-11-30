package org.kdr.blaster.dto.post;

import lombok.Data;

@Data
public class ModifyPostRequestDTO {
    private Long id;

    private String title;

    private String content;
}
