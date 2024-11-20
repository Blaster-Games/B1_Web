package org.kdr.blaster.domain.game;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"buffCountPerMatch", "throwableCountPerMatch", "weaponCountPerMatch"})
@Table
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "played_at", nullable = false)
    private LocalDate playedAt;

    @ManyToOne
    @JoinColumn(name = "map_id", nullable = false)
    private GameMap gameMap;

    @OneToMany(mappedBy = "match", fetch = FetchType.LAZY)
    @Builder.Default
    private List<BuffCountPerMatch> buffCountPerMatch = new ArrayList<>();

    @OneToMany(mappedBy = "match", fetch = FetchType.LAZY)
    @Builder.Default
    private List<ThrowableCountPerMatch> throwableCountPerMatch = new ArrayList<>();

    @OneToMany(mappedBy = "match", fetch = FetchType.LAZY)
    @Builder.Default
    private List<WeaponCountPerMatch> weaponCountPerMatch = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        playedAt = LocalDate.now();
    }
}
