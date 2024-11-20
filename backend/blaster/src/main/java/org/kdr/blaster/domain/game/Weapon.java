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
@ToString(exclude = {"weaponCountPerMatch"})
@Table
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "weapon", fetch = FetchType.LAZY)
    @Builder.Default
    private List<WeaponCountPerMatch> weaponCountPerMatch = new ArrayList<>();

}
