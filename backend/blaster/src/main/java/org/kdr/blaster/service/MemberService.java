package org.kdr.blaster.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.domain.GamePlayRecord;
import org.kdr.blaster.domain.member.Member;
import org.kdr.blaster.dto.GamePlayRecordDTO;
import org.kdr.blaster.dto.MemberDTO;
import org.kdr.blaster.repository.MemberRepository;
import org.kdr.blaster.util.AuthenticationUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;

    public GamePlayRecordDTO onLogout() {
        MemberDTO memberDTO = AuthenticationUtil.getAuthenticatedMember();
        Member before = memberRepository.findById(memberDTO.getId())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        before.onLogout();
        Member after = memberRepository.save(before);
        Duration duration = Duration.between(after.getLastGameLoginAt(), after.getLastGameLogoutAt());
        GamePlayRecord record = GamePlayRecord.builder()
                .loginTime(after.getLastGameLoginAt())
                .logoutTime(after.getLastGameLogoutAt())
                .duration(duration.toMinutes())
                .build();
        return GamePlayRecordDTO.entityToDTO(record);
    }
}
