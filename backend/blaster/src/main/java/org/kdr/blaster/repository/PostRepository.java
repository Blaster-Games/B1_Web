package org.kdr.blaster.repository;

import org.kdr.blaster.domain.board.Post;
import org.kdr.blaster.repository.search.PostSearch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostSearch {

    @EntityGraph(attributePaths = {"member"})
    Optional<Post> findByIdAndDeletedFalse(Long id);

    @EntityGraph(attributePaths = {"imageUrls"})
    @Query("select p from Post p where p.id = :id")
    Optional<Post> findByIdWithUrls(Long id);
}
