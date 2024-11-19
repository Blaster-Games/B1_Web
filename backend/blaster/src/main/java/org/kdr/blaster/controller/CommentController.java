package org.kdr.blaster.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.dto.comment.ChangeCommentRequestDTO;
import org.kdr.blaster.dto.comment.CreateCommentRequestDTO;
import org.kdr.blaster.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
@Log4j2
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/list/{postId}")
    public ResponseEntity<?> getCommentList(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentList(postId));
    }

    @PostMapping("/")
    public ResponseEntity<?> createComment(@RequestBody CreateCommentRequestDTO createCommentRequestDTO) {
        return ResponseEntity.ok(commentService.createComment(createCommentRequestDTO));
    }

    @PutMapping("/")
    public ResponseEntity<?> changeComment(@RequestBody ChangeCommentRequestDTO changeCommentRequestDTO) {
        return ResponseEntity.ok(commentService.changeComment(changeCommentRequestDTO));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.deleteComment(commentId));
    }
}
