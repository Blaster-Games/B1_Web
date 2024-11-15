package org.kdr.blaster.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.dto.PostListDTO;
import org.kdr.blaster.dto.PostPageRequestDTO;
import org.kdr.blaster.dto.PageResponseDTO;
import org.kdr.blaster.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
@Log4j2
public class PostController {

    private final PostService postService;

    @GetMapping("list")
    public PageResponseDTO<PostListDTO> getPosts(PostPageRequestDTO postPageRequestDTO) {
        log.info(postPageRequestDTO);
        return postService.getPosts(postPageRequestDTO);
    }
}
