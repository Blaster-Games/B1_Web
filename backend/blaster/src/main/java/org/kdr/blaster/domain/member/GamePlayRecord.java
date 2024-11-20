package org.kdr.blaster.domain.member;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class GamePlayRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "login_time")
    private LocalDateTime loginTime;

    @Column(nullable = false, name = "logout_time")
    private LocalDateTime logoutTime;

    @Column(nullable = false)
    private Long duration;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
