package com.example.postProject.service;

import com.example.postProject.domain.Board;
import com.example.postProject.domain.Comment;
import org.springframework.stereotype.Service;

public interface CommentService {
    public Iterable<Comment> findAllComments(Board board);
    public void saveComment(Comment comment);
}
