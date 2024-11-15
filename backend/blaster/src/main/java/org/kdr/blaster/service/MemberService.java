package org.kdr.blaster.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.domain.GamePlayRecord;
import org.kdr.blaster.domain.member.Member;
import org.kdr.blaster.domain.member.UserRole;
import org.kdr.blaster.dto.*;
import org.kdr.blaster.repository.MemberRepository;
import org.kdr.blaster.util.AuthenticationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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

    public boolean nicknameCheck(String nickname) {
        Optional<Member> byNickname = memberRepository.findByNickname(nickname);
        return byNickname.isPresent();
    }

    public boolean emailCheck(String email) {
        Optional<Member> byEmail = memberRepository.findByEmail(email);
        return byEmail.isPresent();
    }

    public SignUpResponseDTO signUp(SignUpRequestDTO signUpDTO) {
        Member userDraft = Member.builder()
                .email(signUpDTO.getEmail())
                .nickname(signUpDTO.getNickname())
                .password(passwordEncoder.encode(signUpDTO.getPassword()))
                .userRole(UserRole.USER)
                .build();

        Member newUser = memberRepository.save(userDraft);
        return new SignUpResponseDTO(newUser.getNickname(), newUser.getEmail());
    }

    public ResponseEntity<?> changeNickname(ChangeNicknameDTO changeNicknameDTO) {
        if (memberRepository.findByNickname(changeNicknameDTO.getNickname()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "이미 사용 중인 닉네임입니다."));
        }

        MemberDTO memberDTO = AuthenticationUtil.getAuthenticatedMember();
        Member before = memberRepository.findById(memberDTO.getId())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        before.changeNickname(changeNicknameDTO.getNickname());
        before.onUpdate();
        Member after = memberRepository.save(before);
        return ResponseEntity.ok(Map.of("message", after.getNickname()));
    }

    public ResponseEntity<?> changePassword(ChangePasswordDTO changePasswordDTO) {
        MemberDTO memberDTO = AuthenticationUtil.getAuthenticatedMember();
        Member before = memberRepository.findById(memberDTO.getId())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), before.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Incorrect_OldPassword"));
        }

        before.changePassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        Member after = memberRepository.save(before);
        return ResponseEntity.ok(Map.of("message", "success"));
    }
}
