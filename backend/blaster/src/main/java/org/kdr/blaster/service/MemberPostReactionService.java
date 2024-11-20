package org.kdr.blaster.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.domain.board.MemberPostReaction;
import org.kdr.blaster.domain.board.Post;
import org.kdr.blaster.domain.board.Reaction;
import org.kdr.blaster.domain.member.Member;
import org.kdr.blaster.dto.reaction.PostReactionChangeRequestDTO;
import org.kdr.blaster.dto.reaction.PostReactionRespondDTO;
import org.kdr.blaster.repository.MemberPostReactionRepository;
import org.kdr.blaster.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberPostReactionService {

    private final MemberPostReactionRepository memberPostReactionRepository;
    private final PostRepository postRepository;

    private final MemberService memberService;

    public PostReactionRespondDTO getReaction(Long postId) {
        Member member = memberService.getAuthenticatedMember();
        Optional<MemberPostReaction> optionalReaction = memberPostReactionRepository.findByMemberAndPost_Id(member, postId);
        if (optionalReaction.isEmpty()) {
            return new PostReactionRespondDTO(Reaction.NONE);
        }
            return new PostReactionRespondDTO(optionalReaction.get().getReaction());
    }

    public PostReactionRespondDTO like(Long postId) {
        Member member = memberService.getAuthenticatedMember();
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다."));
        Optional<MemberPostReaction> optionalReaction =
                memberPostReactionRepository.findByMemberAndPost(member, post);

        MemberPostReaction reaction;
        if (optionalReaction.isEmpty()) {
            reaction = MemberPostReaction.builder()
                    .member(member)
                    .post(post)
                    .reaction(Reaction.LIKE)
                    .build();
            post.incrementLikeCount();
        } else {
            reaction = optionalReaction.get();
            if (reaction.getReaction() == Reaction.DISLIKE) {
                post.incrementLikeCount();
                post.decrementDislikeCount();
            } else if (reaction.getReaction() == Reaction.NONE) {
                post.incrementLikeCount();
            }
            reaction.changeReaction(Reaction.LIKE);
        }
        postRepository.save(post);
        return new PostReactionRespondDTO(memberPostReactionRepository.save(reaction).getReaction());
    }

    public PostReactionRespondDTO dislike(Long postId) {
        Member member = memberService.getAuthenticatedMember();
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다."));
        Optional<MemberPostReaction> optionalReaction =
                memberPostReactionRepository.findByMemberAndPost(member, post);

        MemberPostReaction reaction;
        if (optionalReaction.isEmpty()) {
            reaction = MemberPostReaction.builder()
                    .member(member)
                    .post(post)
                    .reaction(Reaction.DISLIKE)
                    .build();
            post.incrementDislikeCount();
        } else {
            reaction = optionalReaction.get();
            if (reaction.getReaction() == Reaction.LIKE) {
                post.decrementLikeCount();
                post.incrementDislikeCount();
            } else if (reaction.getReaction() == Reaction.NONE) {
                post.incrementDislikeCount();
            }
            reaction.changeReaction(Reaction.DISLIKE);
        }
        postRepository.save(post);
        return new PostReactionRespondDTO(memberPostReactionRepository.save(reaction).getReaction());
    }

    public PostReactionRespondDTO none(Long postId) {
        Member member = memberService.getAuthenticatedMember();
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다."));
        Optional<MemberPostReaction> optionalReaction =
                memberPostReactionRepository.findByMemberAndPost(member, post);

        MemberPostReaction reaction;
        if (optionalReaction.isEmpty()) {
            reaction = MemberPostReaction.builder()
                    .member(member)
                    .post(post)
                    .reaction(Reaction.NONE)
                    .build();
        } else {
            reaction = optionalReaction.get();
            if (reaction.getReaction() == Reaction.LIKE) {
                post.decrementLikeCount();
            } else if (reaction.getReaction() == Reaction.DISLIKE) {
                post.decrementDislikeCount();
            }
            reaction.changeReaction(Reaction.NONE);
        }
        postRepository.save(post);
        return new PostReactionRespondDTO(memberPostReactionRepository.save(reaction).getReaction());
    }

//    public Long react(PostReactionChangeRequestDTO postReactionChangeRequestDTO) {
//        Member member = memberService.getAuthenticatedMember();
//
//        Optional<MemberPostReaction> optionalReaction
//                = memberPostReactionRepository
//                .findByMember_IdAndPost_Id(member.getId(), postReactionChangeRequestDTO.getPostId());
//
//        MemberPostReaction reaction;
//
//        if (optionalReaction.isEmpty()) {
//            reaction = MemberPostReaction.builder()
//                    .member(member)
//                    .post(postRepository
//                            .findById(
//                                    postReactionChangeRequestDTO
//                                            .getPostId())
//                            .orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다.")))
//                    .reaction(postReactionChangeRequestDTO.getReaction())
//                    .build();
//        } else {
//            reaction = optionalReaction.get();
//            reaction.changeReaction(postReactionChangeRequestDTO.getReaction());
//        }
//
//        return memberPostReactionRepository.save(reaction).getId();
//    }
}
