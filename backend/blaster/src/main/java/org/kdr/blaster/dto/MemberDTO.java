package org.kdr.blaster.dto;

import lombok.*;
import org.kdr.blaster.domain.member.UserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@ToString
public class MemberDTO extends User {

    private Long id;

    private String email;

    private UserRole userRole;

    private String nickname;

    public MemberDTO(String username, String password, UserRole userRole, Long id, String nickname) {
        super(username, password, List.of(new SimpleGrantedAuthority("ROLE_" + userRole.name())));
        this.id = id;
        this.email = username;
        this.userRole = userRole;
        this.nickname = nickname;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("email", email);
        dataMap.put("userRole", userRole);
        dataMap.put("nickname", nickname);
        return dataMap;
    }

}
