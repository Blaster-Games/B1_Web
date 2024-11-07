package org.kdr.blaster.domain.board;

import jakarta.persistence.*;
import lombok.*;
import org.kdr.blaster.domain.member.Member;

import java.time.LocalDateTime;

//@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class ChildComment {

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
    private boolean deleted;

//    @ManyToOne
//    @JoinColumn(name = "author_id", nullable = false)
//    private Member member;

//    @ManyToOne
//    @JoinColumn(name = "parent_comment_id", nullable = false)
//    private Comment comment;

//    @ManyToOne
//    @JoinColumn(name = "post_id", nullable = false)
//    private Post post;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void deleteChildComment() {
        this.deleted = true;
    }
}
