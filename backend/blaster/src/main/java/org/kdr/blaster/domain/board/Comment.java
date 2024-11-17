package org.kdr.blaster.domain.board;

import jakarta.persistence.*;
import lombok.*;
import org.kdr.blaster.domain.member.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = "reactions")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private int likeCount;

    @Column(nullable = false)
    private int dislikeCount;

    @Column(nullable = false)
    private boolean deleted;

//    @Column(nullable = false)
//    private int childCommentCount;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @OneToMany(mappedBy = "comment")
    @Builder.Default
    private List<MemberCommentReaction> reactions = new ArrayList<>();

//    @OneToMany(mappedBy = "comment")
//    private List<ChildComment> childComments;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        this.likeCount--;
    }

    public void incrementDislikeCount() {
        this.dislikeCount++;
    }

    public void decrementDislikeCount() {
        this.dislikeCount--;
    }

//    public void incrementChildCommentCount() {
//        this.childCommentCount++;
//    }
//
//    public void decrementChildCommentCount() {
//        this.childCommentCount--;
//    }

    public void deleteComment() {
        this.deleted = true;
    }

}
