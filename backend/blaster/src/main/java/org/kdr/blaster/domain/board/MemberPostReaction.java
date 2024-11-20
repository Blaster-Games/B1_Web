package org.kdr.blaster.domain.board;

import jakarta.persistence.*;
import lombok.*;
import org.kdr.blaster.domain.member.Member;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of = {"member", "post"})
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"member_id", "post_id"})
        }
)
public class MemberPostReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Enumerated(EnumType.STRING)
    @JoinColumn(nullable = false)
    private Reaction reaction;

    public void changeReaction(Reaction reaction) {
        this.reaction = reaction;
    }
}
