package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.CommentDTO;
import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/save")
    public String saveForm() {
        return "boardPages/boardSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        boardService.save(boardDTO);
        return "redirect:/board/";
    }

    @GetMapping("/")
    public String findAll(Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        System.out.println("boardDTOList = " + boardDTOList);
        return "boardPages/boardList";
    }

    @GetMapping // <-- 여기
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model,
                         @RequestParam(value = "type", required = false, defaultValue = "") String type,
                         @RequestParam(value = "q", required = false, defaultValue = "") String q) { // <-- 여기
        System.out.println("page = " + pageable.getPageNumber());
        Page<BoardDTO> boardDTOS = boardService.paging(pageable, type, q);
        if (boardDTOS.getTotalElements() == 0) {
            model.addAttribute("boardList", null);
        } else {
            model.addAttribute("boardList", boardDTOS);
        }
        // 시작페이지, 마지막페이지 값 계산
        // 하단에 보여줄 페이지 갯수 = 3으로 가보자고
        int blockLimit = 10;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        // endPage 값이 maxPage 값보다 크면 :: endPage = maxPage
        // endPage 값이 maxPage 값보다 작으면
        int endPage = ((startPage + blockLimit - 1) < boardDTOS.getTotalPages()) ? startPage + blockLimit - 1 : boardDTOS.getTotalPages();
//        if ((startPage + blockLimit - 1) < boardDTOS.getTotalPages()) {
//            endPage = startPage + blockLimit - 1;
//        } else {
//            endPage = boardDTOS.getTotalPages()
//        }
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("type", type);
        model.addAttribute("q", q);
        return "boardPages/boardPaging";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                           @RequestParam(value = "type", required = false, defaultValue = "title") String type,
                           @RequestParam(value = "q", required = false, defaultValue = "") String q) {
        boardService.updateHits(id);  // 항상 boardDetail 구현할 때에는 조회수 처리부터 하자
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        model.addAttribute("q", q);
        try {
            BoardDTO boardDTO = boardService.findById(id);
            model.addAttribute("board", boardDTO);
            List<CommentDTO> commentDTOList = commentService.findAll(id);
            if (commentDTOList.size() > 0) {
                model.addAttribute("commentList", commentDTOList);
            } else {
                model.addAttribute("commentList", null);
            }
            return "boardPages/boardDetail";
        } catch (NoSuchElementException e) {
            return "boardPages/boardNotFound";
        }
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "boardPages/boardUpdate";
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody BoardDTO boardDTO) {
        boardService.update(boardDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        boardService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
