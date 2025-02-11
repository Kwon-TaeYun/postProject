package com.example.postProject.service;

import com.example.postProject.domain.Board;
import com.example.postProject.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface BoardService {
    public Iterable<Board> findAllBoards();
    public Page<Board> findAllBoardsByPage(Pageable pageable);
    public Board findByBoardId(Long id);
    public void editBoard(Board board);

    public void saveBoard(Board board);
    public void deleteBoardById(Long id);

}
