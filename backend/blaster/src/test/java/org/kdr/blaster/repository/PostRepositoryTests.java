package org.kdr.blaster.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.kdr.blaster.domain.board.Category;
import org.kdr.blaster.domain.board.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;

@SpringBootTest
@Log4j2
public class PostRepositoryTests {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void testInsert() {
        for (int i = 101; i <= 200; i++) {
            Post postTemp = Post.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .category(Category.GENERAL)
                    .member(memberRepository.findById((long) (i % 2 + 2)).orElseThrow())
                    .game(gameRepository.findById(1L).orElseThrow())
                    .build();

            postRepository.save(postTemp);
        }
    }

    @Test
    public void testPaging() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        Page<Post> result = postRepository.findAll(pageable);
        log.info(result.getTotalElements());
        log.info(result.getContent());
    }
}
