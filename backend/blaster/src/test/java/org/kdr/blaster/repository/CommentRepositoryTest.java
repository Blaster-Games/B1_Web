package org.kdr.blaster.repository;

import org.junit.jupiter.api.Test;
import org.kdr.blaster.domain.board.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void insertTest() {
        for (long i = 1; i <= 100; i++) {
            Comment c = Comment.builder()
                    .content("content" + i)
                    .member(memberRepository.findById(i).orElseThrow())
                    .post(postRepository.findById(200L).orElseThrow())
                    .build();
            commentRepository.save(c);
        }
    }
}
