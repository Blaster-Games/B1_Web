package org.kdr.blaster.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.dto.member.ChangeNicknameDTO;
import org.kdr.blaster.dto.member.ChangePasswordDTO;
import org.kdr.blaster.dto.member.SignUpRequestDTO;
import org.kdr.blaster.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Log4j2
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/logout/game")
    public ResponseEntity<?> onLogout() {
        return ResponseEntity.ok(memberService.onLogout());
    }

    @GetMapping("/signup/nickname/{nickname}")
    public ResponseEntity<?> nicknameCheck(@PathVariable String nickname) {
        return ResponseEntity.ok(memberService.nicknameCheck(nickname));
    }

    @GetMapping("/signup/email/{email}")
    public ResponseEntity<?> emailCheck(@PathVariable String email) {
        return ResponseEntity.ok(memberService.emailCheck(email));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDTO signUpDTO) {
        return ResponseEntity.ok(memberService.signUp(signUpDTO));
    }

    @PutMapping("/nickname")
    public ResponseEntity<?> changeNickname(@RequestBody ChangeNicknameDTO changeNicknameDTO) {
        return memberService.changeNickname(changeNicknameDTO);
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        return memberService.changePassword(changePasswordDTO);
    }
}
