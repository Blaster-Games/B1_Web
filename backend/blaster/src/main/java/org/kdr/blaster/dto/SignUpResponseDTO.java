package org.kdr.blaster.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpResponseDTO {
    private String nickname;
    private String email;
}
