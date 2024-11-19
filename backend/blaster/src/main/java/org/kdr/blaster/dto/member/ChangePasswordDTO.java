package org.kdr.blaster.dto.member;

import lombok.Getter;

@Getter
public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
}
