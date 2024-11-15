package org.kdr.blaster.domain;

import jakarta.persistence.*;
import lombok.*;
import org.kdr.blaster.domain.board.Post;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = "posts")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "game")
    private List<Post> posts;
}
