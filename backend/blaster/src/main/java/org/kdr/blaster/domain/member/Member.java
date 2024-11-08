package org.kdr.blaster.domain.member;

import jakarta.persistence.*;
import lombok.*;
import org.kdr.blaster.domain.board.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"posts", "comments", "memberPostReaction", "memberCommentReactions", "gamePlayRecords"})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_game_login_at")
    private LocalDateTime lastGameLoginAt;

    @Column(name = "last_game_logout_at")
    private LocalDateTime lastGameLogoutAt;

    @Column(nullable = false, name = "withdrawal_status")
    private boolean withdrawalStatus;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

//    @OneToMany(mappedBy = "member")
//    private List<ChildComment> childComments;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<MemberPostReaction> memberPostReaction = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<MemberCommentReaction> memberCommentReactions = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<GamePlayRecord> gamePlayRecords = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void onLogin() {
        lastGameLoginAt = LocalDateTime.now();
    }

    public void onLogout() {
        lastGameLogoutAt = LocalDateTime.now();
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
