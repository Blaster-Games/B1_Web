package org.kdr.blaster.controller;

import lombok.RequiredArgsConstructor;
import org.kdr.blaster.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/logout/game")
    public ResponseEntity<?> onLogout() {
        return ResponseEntity.ok(memberService.onLogout());
    }
}
