package org.kdr.blaster.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.domain.board.Comment;
import org.kdr.blaster.domain.board.Post;
import org.kdr.blaster.domain.member.Member;
import org.kdr.blaster.dto.comment.ChangeCommentRequestDTO;
import org.kdr.blaster.dto.comment.CommentResponseDTO;
import org.kdr.blaster.dto.comment.CreateCommentRequestDTO;
import org.kdr.blaster.mapper.CommentMapper;
import org.kdr.blaster.repository.CommentRepository;
import org.kdr.blaster.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberService memberService;

    public List<CommentResponseDTO> getCommentList(Long postId) {
        List<Comment> comments = commentRepository.findByPost_IdAndDeletedFalse(postId);
        return comments.stream().map(CommentMapper::toCommentResponseDTO).toList();
    }

    public Map<String, Integer> createComment(CreateCommentRequestDTO createCommentRequestDTO) {
        Member author = memberService.getAuthenticatedMember();
        Post post = postRepository
                .findById(createCommentRequestDTO.getPostId())
                .orElseThrow(() -> new NoSuchElementException("해당 게시글을 찾지 못하였습니다."));
        if (post.isDeleted()) {
            throw new NoSuchElementException("삭제된 게시물입니다.");
        }
        Comment temp = Comment.builder()
                .content(createCommentRequestDTO.getContent())
                .member(author)
                .post(post)
                .build();
        commentRepository.save(temp);
        post.incrementCommentCount();
        postRepository.save(post);
        return Map.of("commentCount", post.getCommentCount());
    }

    public Map<String, String> changeComment(ChangeCommentRequestDTO changeCommentRequestDTO) {
        Comment comment = commentRepository
                .findById(changeCommentRequestDTO.getCommentId())
                .orElseThrow(() -> new NoSuchElementException("해당 댓글을 찾지 못하였습니다."));
        if (comment.isDeleted()) {
            throw new NoSuchElementException("삭제된 댓글을 찾지 못하였습니다.");
        }
        comment.changeContent(changeCommentRequestDTO.getContent());
        commentRepository.save(comment);
        return Map.of("message", "success");
    }

    public Map<String, Integer> deleteComment(Long commentId) {
        Comment comment = commentRepository
                .findByIdWithPost(commentId)
                .orElseThrow(() -> new NoSuchElementException("해당 댓글을 찾지 못하였습니다."));
        if (comment.isDeleted()) {
            throw new NoSuchElementException("이미 삭제된 댓글입니다.");
        }
        comment.deleteComment();
        commentRepository.save(comment);
        comment.getPost().decrementCommentCount();
        postRepository.save(comment.getPost());
        return Map.of("commentCount", comment.getPost().getCommentCount());
    }
}
