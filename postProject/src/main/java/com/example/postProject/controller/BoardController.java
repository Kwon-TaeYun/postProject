package com.example.postProject.controller;


import com.example.postProject.domain.Board;
import com.example.postProject.domain.Comment;
import com.example.postProject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.postProject.service.BoardService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;
    @GetMapping("/list")
    public String home(
            Model model,
            @RequestParam(name="page", required = false, defaultValue = "1")int page,
            @RequestParam(name="size", required = false, defaultValue = "5")int size
    ){
        Pageable pageable = PageRequest.of(page-1, size);
        model.addAttribute("boards", boardService.findAllBoardsByPage(pageable));
        model.addAttribute("currentPage", page);
        return "home";
    }

    @GetMapping("/view")
    public String view(@RequestParam(name="id")Long id, Model model){
        Board board = boardService.findByBoardId(id);
        model.addAttribute("board", board);
        return "view";
    }

    @GetMapping("/delete")
    public String deleteForm(@RequestParam(name="id")Long id, Model model){
        Board board = boardService.findByBoardId(id);
        model.addAttribute("board", board);
        model.addAttribute("boardPassword", board.getPassword());
        return "delete";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(name="id") Long id, @RequestParam("password")String password
    ,RedirectAttributes redirectAttributes) {
        if(password.equals(boardService.findByBoardId(id).getPassword())) {
            boardService.deleteBoardById(id);  // 서비스에서 해당 게시글 삭제
            return "redirect:/boards/list";
        } else {
            redirectAttributes.addFlashAttribute("error", "비밀번호 인증에 실패하였습니다.");
            return "redirect:/boards/delete?id=" + id;  // 삭제 후 게시글 목록 페이지로 리다이렉트
        }
    }

    @GetMapping("/edit")
    public String updateForm(@RequestParam(name="id")Long id, Model model){
        Board board = boardService.findByBoardId(id);
        model.addAttribute("board",board);
        return "update";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute Board board, @RequestParam("password")String password, Model model,
                         RedirectAttributes redirectAttributes){

        if(password.equals(boardService.findByBoardId(board.getId()).getPassword())) {
            if (board.getCreatedAt() == null) {
                board.setCreatedAt(LocalDateTime.now());
            }
            boardService.editBoard(board);
            board.setId(board.getId());
            return "redirect:/boards/view?id="+board.getId();
        } else {
            redirectAttributes.addFlashAttribute("error", "비밀번호 인증에 실패하였습니다.");
            return "redirect:/boards/edit?id=" + board.getId();
        }
    }

    @GetMapping("/write")
    public String writeForm(Model model){
        model.addAttribute("board", new Board());
        return "write";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute Board board){
        boardService.saveBoard(board);
        return "redirect:/boards/list";
    }

    @GetMapping("/comment")
    public String commentForm(@RequestParam(name="id")Long id, @ModelAttribute Board board, Model model){
        id = board.getId();
        model.addAttribute("board", board);
        model.addAttribute("comments", commentService.findAllComments(board));
        model.addAttribute("comment", new Comment());
        return "commentList";
    }

    @PostMapping("/comment")
    public String comment(@ModelAttribute Comment comment, @ModelAttribute Board board){
        //작성자 확인 방법이 없으므로, 예시를 추가
        comment.setAuthor("김민수");
        comment.setBoardId(board.getId());
        commentService.saveComment(comment);
        return "redirect:/boards/comment?id=" + comment.getBoardId();
    }


}
