package org.kdr.blaster.repository;

import org.kdr.blaster.domain.board.Post;
import org.kdr.blaster.repository.search.PostSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostSearch {
}
