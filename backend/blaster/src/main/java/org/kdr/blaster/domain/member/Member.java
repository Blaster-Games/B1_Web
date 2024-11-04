package org.kdr.blaster.domain.member;

import jakarta.persistence.*;
import lombok.*;
import org.kdr.blaster.domain.board.MemberCommentReaction;
import org.kdr.blaster.domain.board.MemberPostReaction;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"memberPostReaction", "memberCommentReactions"})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserRole userRole;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false, name = "withdrawal_status")
    private boolean withdrawalStatus;

    @OneToMany(mappedBy = "member")
    private List<MemberPostReaction> memberPostReaction;

    @OneToMany(mappedBy = "member")
    private List<MemberCommentReaction> memberCommentReactions;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
