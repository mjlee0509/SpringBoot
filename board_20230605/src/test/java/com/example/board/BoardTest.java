package com.example.board;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.CommentDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.repo.BoardRepository;
import com.example.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;

@SpringBootTest
public class BoardTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    private BoardDTO newBoards(int i) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardTitle("테스트게시물"+i);
        boardDTO.setBoardWriter("테스트돌이");
        boardDTO.setBoardPass("test"+i);
        boardDTO.setBoardContents("테스트게시물 "+i+" 입니다 껄껄");
        return boardDTO;
    }
    private BoardDTO newBoard() {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardTitle("테스트게시물");
        boardDTO.setBoardWriter("테스트돌이");
        boardDTO.setBoardPass("test");
        boardDTO.setBoardContents("그저 테스트게시물 입니다 껄껄");
        return boardDTO;
    }

//    @Test
//    public void testData() {
//        for (int i=1; i<=10; i++) {
//            boardService.save(newBoards(i));
//        }
//    }

//    @Test
//    public void testDatum() {
//        boardService.save(newBoard());
//    }

//    @Test
//    @Transactional
//    @Rollback
//    @DisplayName("글쓰기 테스트")
//    public void boardSaveTest() {
//        BoardDTO boardDTO = newBoards(999);
//        Long saveId = boardService.save(boardDTO);
//        BoardDTO s = boardService.findById(saveId);
//    }

    @Test
    @Transactional
    @DisplayName("참조관계 확인")
    public void test1() {
        BoardEntity boardEntity = boardRepository.findById(1L).get();
        // boardEntity로 첨부된 파일의 이름 조회
        // 부모 Entity에서 자식 Entity를 조회하는 경우 @Transactional 필요
        System.out.println("첨부파일 이름 = " + boardEntity.getBoardFileEntityList().get(0).getStoredFileName());
    }

}
