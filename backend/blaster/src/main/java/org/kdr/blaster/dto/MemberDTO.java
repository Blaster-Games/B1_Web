package org.kdr.blaster.dto;

import lombok.*;
import org.kdr.blaster.domain.member.UserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private LocalDateTime createdAt;

    public MemberDTO(String username, String password, UserRole userRole, Long id, String nickname, LocalDateTime createdAt) {
        super(username, password, List.of(new SimpleGrantedAuthority("ROLE_" + userRole.name())));
        this.id = id;
        this.email = username;
        this.userRole = userRole;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. MM. dd");
        dataMap.put("email", email);
        dataMap.put("userRole", userRole);
        dataMap.put("nickname", nickname);
        dataMap.put("createdAt", createdAt.format(formatter));
        return dataMap;
    }

}
