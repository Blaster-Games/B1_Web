package org.kdr.blaster.util;

import org.kdr.blaster.dto.MemberDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtil {

    public static MemberDTO getAuthenticatedMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof MemberDTO memberDTO) {
            return memberDTO;
        }

        throw new RuntimeException("User is not authenticated");
    }
}
