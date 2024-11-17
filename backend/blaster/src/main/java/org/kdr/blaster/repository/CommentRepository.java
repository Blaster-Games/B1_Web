package org.kdr.blaster.repository;

import org.kdr.blaster.domain.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
