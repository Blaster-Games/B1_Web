package org.kdr.blaster.repository;

import org.kdr.blaster.domain.board.MemberPostReaction;
import org.kdr.blaster.domain.board.Post;
import org.kdr.blaster.domain.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberPostReactionRepository extends JpaRepository<MemberPostReaction, Long> {
    @EntityGraph(attributePaths = {"member", "post"})
    Optional<MemberPostReaction> findByMemberAndPost_Id(Member member, Long Id);

    @EntityGraph(attributePaths = {"member", "post"})
    Optional<MemberPostReaction> findByMemberAndPost(Member member, Post post);

//    @EntityGraph(attributePaths = {"member", "post"})
//    Optional<MemberPostReaction> findByMember_IdAndPost_Id(Long memberId, Long postId);

}
