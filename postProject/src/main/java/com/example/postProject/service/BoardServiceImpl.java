package com.example.postProject.service;

import com.example.postProject.domain.Board;
import com.example.postProject.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.postProject.repository.BoardRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository repository;
    @Override
    public Iterable<Board> findAllBoards() {
        return repository.findAll();
    }

    @Override
    public Page<Board> findAllBoardsByPage(Pageable pageable) {
        return repository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by(Sort.Direction.DESC,"id")
                )
        );
    }

    @Override
    public Board findByBoardId(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void editBoard(Board board) {
        Board existingBoard = repository.findById(board.getId()).orElse(null);

        existingBoard.setName(board.getName());
        existingBoard.setTitle(board.getTitle());
        existingBoard.setContent(board.getContent());

        // Don't overwrite createdAt if it's already set
        if (existingBoard.getCreatedAt() == null) {
            existingBoard.setCreatedAt(LocalDateTime.now());  // Set if not already set
        }

        repository.save(existingBoard);
    }

    @Override
    public void saveBoard(Board board) {
        if (board.getCreatedAt() == null) {
            board.setCreatedAt(LocalDateTime.now());  // Set if not already set
        }

        repository.save(board);
    }

    @Override
    public void deleteBoardById(Long id) {
        repository.deleteById(id);
    }


}
