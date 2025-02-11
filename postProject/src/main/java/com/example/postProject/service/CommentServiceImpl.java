package com.example.postProject.service;

import com.example.postProject.domain.Board;
import com.example.postProject.domain.Comment;
import com.example.postProject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository repository;

    @Override
    public Iterable<Comment> findAllComments(Board board) {
        return repository.findAllByBoardId(board.getId());
    }

    @Override
    public void saveComment(Comment comment) {
        if (comment.getId() != null) {
            comment.setId(null);  // 새 댓글이므로 ID를 null로 설정하여 삽입 처리
        }
        if (comment.getCreatedAt() == null) {
            comment.setCreatedAt(LocalDateTime.now());  // Set if not already set
        }


        repository.save(comment);
    }
}
