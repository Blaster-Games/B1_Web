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
@ToString(exclude = {"buffCountPerMatch"})
@Table
public class Buff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "buff", fetch = FetchType.LAZY)
    @Builder.Default
    private List<BuffCountPerMatch> buffCountPerMatch = new ArrayList<>();
}
