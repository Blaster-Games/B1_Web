package org.kdr.blaster.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.domain.board.Post;
import org.kdr.blaster.dto.PostDTO;
import org.kdr.blaster.dto.PostListDTO;
import org.kdr.blaster.dto.PostPageRequestDTO;
import org.kdr.blaster.dto.PageResponseDTO;
import org.kdr.blaster.exception.PageOutOfBoundsException;
import org.kdr.blaster.mapper.PostMapper;
import org.kdr.blaster.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class PostService {

    private final PostRepository postRepository;

    public PageResponseDTO<PostListDTO> getPosts(PostPageRequestDTO postPageRequestDTO) {
        if (postPageRequestDTO.getPage() < 1) {
            postPageRequestDTO.setPage(1);
        }
        if (postPageRequestDTO.getSize() < 1) {
            postPageRequestDTO.setSize(10);
        }
        Page<PostListDTO> page = postRepository.getPosts(postPageRequestDTO);
        PageResponseDTO<PostListDTO> result = new PageResponseDTO<>(page);

        if (result.getCurrent() > 1 && result.getCurrent() > result.getTotalPages()) {
            throw new PageOutOfBoundsException("요청한 페이지(" + result.getCurrent() + ")가 총 페이지 수(" + result.getTotalPages() + ")를 초과했습니다.");
        }

        return result;
    }

    public PostDTO getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 게시물을 찾지 못하였습니다."));
        if (post.isDeleted()) {
            throw new NoSuchElementException("해당 게시물을 찾지 못하였습니다.");
        }
        return PostMapper.toPostDTO(post);
    }
}
