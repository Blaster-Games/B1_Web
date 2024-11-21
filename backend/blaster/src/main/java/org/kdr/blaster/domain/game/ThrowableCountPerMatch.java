package org.kdr.blaster.domain.game;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"match_id", "throwable_id"}))
public class ThrowableCountPerMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int count;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private GameMatch match;

    @ManyToOne
    @JoinColumn(name = "throwable_id", nullable = false)
    private ThrowableObject throwableObject;
}
