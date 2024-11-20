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
@ToString(exclude = {"matches"})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "mod"}))
public class GameMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String mod;

    @OneToMany(mappedBy = "map", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Match> matches = new ArrayList<>();
}
