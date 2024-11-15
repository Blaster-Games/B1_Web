package org.kdr.blaster.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.domain.board.Post;
import org.kdr.blaster.dto.PostListDTO;
import org.kdr.blaster.dto.PostPageRequestDTO;
import org.kdr.blaster.dto.PageResponseDTO;
import org.kdr.blaster.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class PostService {

    private final PostRepository postRepository;

    public PageResponseDTO<PostListDTO> getPosts(PostPageRequestDTO postPageRequestDTO) {
        Page<PostListDTO> page = postRepository.getPosts(postPageRequestDTO);
        return new PageResponseDTO<PostListDTO>(page);
    }
}
