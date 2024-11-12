package org.kdr.blaster.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignUpRequestDTO {
    private String nickname;
    private String email;
    private String password;
}
