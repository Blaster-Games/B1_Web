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
@ToString(exclude = {"throwableCountPerMatch"})
@Table
public class ThrowableObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "throwableObject", fetch = FetchType.LAZY)
    @Builder.Default
    private List<ThrowableCountPerMatch> throwableCountPerMatch = new ArrayList<>();
}
