package org.kdr.blaster.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.dto.post.CreatePostRequestDTO;
import org.kdr.blaster.dto.post.PostPageRequestDTO;
import org.kdr.blaster.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
@Log4j2
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    public ResponseEntity<?> getPosts(PostPageRequestDTO postPageRequestDTO) {
        log.info(postPageRequestDTO.getCategory());
        return ResponseEntity.ok(postService.getPosts(postPageRequestDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @GetMapping("/reaction/{id}")
    public ResponseEntity<?> getReactionCount(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getReactionCount(id));
    }

    @PostMapping("")
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequestDTO createPostRequestDTO) {
        return ResponseEntity.ok(postService.createPost(createPostRequestDTO));
    }
}
