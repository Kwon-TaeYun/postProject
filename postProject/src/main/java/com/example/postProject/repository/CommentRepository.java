package com.example.postProject.repository;

import com.example.postProject.domain.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    Iterable<Comment> findAllByBoardId(Long boardId);
}
