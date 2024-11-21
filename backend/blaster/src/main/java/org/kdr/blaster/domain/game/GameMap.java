package org.kdr.blaster.domain.game;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"gameMatches"})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "mod"}))
public class GameMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String gameMod;

    @OneToMany(mappedBy = "gameMap", fetch = FetchType.LAZY)
    @Builder.Default
    private List<GameMatch> gameMatches = new ArrayList<>();
}
