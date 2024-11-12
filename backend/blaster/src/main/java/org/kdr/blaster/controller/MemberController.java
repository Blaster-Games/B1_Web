package org.kdr.blaster.controller;

import lombok.RequiredArgsConstructor;
import org.kdr.blaster.dto.SignUpRequestDTO;
import org.kdr.blaster.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
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
}
