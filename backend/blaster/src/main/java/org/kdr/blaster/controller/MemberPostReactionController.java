package org.kdr.blaster.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.service.MemberPostReactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reaction/post")
@Log4j2
public class MemberPostReactionController {

    private final MemberPostReactionService memberPostReactionService;

    @GetMapping("/{postId}")
    public ResponseEntity<?> getReaction(@PathVariable Long postId) {
        return ResponseEntity.ok(memberPostReactionService.getReaction(postId));
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<?> like(@PathVariable Long postId) {
        return ResponseEntity.ok(memberPostReactionService.like(postId));
    }

    @PostMapping("/dislike/{postId}")
    public ResponseEntity<?> dislike(@PathVariable Long postId) {
        return ResponseEntity.ok(memberPostReactionService.dislike(postId));
    }

    @PostMapping("/none/{postId}")
    public ResponseEntity<?> none(@PathVariable Long postId) {
        return ResponseEntity.ok(memberPostReactionService.none(postId));
    }

//    @PostMapping("/")
//    public ResponseEntity<?> react(@RequestBody PostReactionChangeRequestDTO postReactionChangeRequestDTO) {
//        return ResponseEntity.ok(memberPostReactionService.react(postReactionChangeRequestDTO));
//    }
}
