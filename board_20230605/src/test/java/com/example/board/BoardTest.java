package com.example.board;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.CommentDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.repo.BoardRepository;
import com.example.board.service.BoardService;
import com.example.board.util.UtlClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    private BoardDTO newBoards(int i) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardTitle("테스트게시물" + i);
        boardDTO.setBoardWriter("테스트돌이");
        boardDTO.setBoardPass("test" + i);
        boardDTO.setBoardContents("테스트게시물 " + i + " 입니다 껄껄");
        boardDTO.setBoardHits(0);
//        boardDTO.setFileAttached(0);
//        boardDTO.setBoardFile(null);
//        boardDTO.setOriginalFileName(null);
//        boardDTO.setStoredFileName(null);
        boardDTO.setCreatedAt("2023년 06월 08일 15:35");
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
//    @Transactional
//    @DisplayName("테스트데이터 생성")
//    public void testData() throws IOException {
//        for (int i=1; i<=30; i++) {
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

    @Test
    @Transactional
    @DisplayName("엔티티 클래스 ToString")
    public void entityToString() {
        BoardEntity boardEntity = boardRepository.findById(1L).get();
        System.out.println("boardEntity = " + boardEntity);
    }
    // 테스트 결과의 시사점 -- Entity는 toString으로 찍어보면 안된다.
    // 무한 반복의 늪에 빠짐

    @Test
    @Transactional
    @DisplayName("findAll을 정렬해서 가져오기")
    public void findAllOrderBy() {
        List<BoardEntity> boardEntityList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardWriter")); // ""안에는 정렬 기준
        boardEntityList.forEach(boardEntity -> {
            System.out.println(BoardDTO.toDTO(boardEntity));
        });
    }

    @Test
    @Transactional
    @DisplayName("(많은) 테스트 데이터 생성")
    @Rollback(value = false)
    public void saveList() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            boardRepository.save(BoardEntity.toSaveEntity(newBoards(i)));
        });
    }

    @Test
    @Transactional
    @DisplayName("페이징 메서드 객체 확인")
    public void pagingMethod() {
        int page = 0; // 요청한 페이지 번호
        int pageLimit = 10; // 한 페이지당 보여줄 글 개수
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        // Page 객체가 제공해주는 메서드 확인
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫페이지인지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막페이지인지 여부

        // Page<BoardEntity>를 Page>BoardDTO>로 변환
        Page<BoardDTO> boardList = boardEntities.map(boardEntity ->
                BoardDTO.builder()
                        // 목록에서 보여줄 것들만 담아가자
                        .id(boardEntity.getId())
                        .boardTitle(boardEntity.getBoardTitle())
                        .boardWriter(boardEntity.getBoardWriter())
                        .createdAt(UtlClass.dataFormat(boardEntity.getCreatedAt()))
                        .boardHits(boardEntity.getBoardHits())
                        .build()
        );
        System.out.println("boardEntities.getContent() = " + boardList.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardEntities.getTotalElements() = " + boardList.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardList.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardEntities.getTotalPages() = " + boardList.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardList.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardEntities.hasPrevious() = " + boardList.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardList.isFirst()); // 첫페이지인지 여부
        System.out.println("boardEntities.isLast() = " + boardList.isLast()); // 마지막페이지인지 여부
    }

    @Test
    @DisplayName("제목으로 검색")
    public void searchTestTitle() {
        List<BoardEntity> boardEntityList = boardRepository.findByBoardTitleContaining("2");
        boardEntityList.forEach(boardEntity -> {
            System.out.println(BoardDTO.toDTO(boardEntity));
        });
    }
    @Test
    @DisplayName("작성자로 검색")
    public void searchTestWriter() {
        List<BoardEntity> boardEntityList = boardRepository.findByBoardWriterContaining("테");
        boardEntityList.forEach(boardEntity -> {
            System.out.println(BoardDTO.toDTO(boardEntity));
        });
    }

    @Test
    @DisplayName("제목으로 검색 내림차순")
    public void searchTestTitleDesc() {
        List<BoardEntity> boardEntityList = boardRepository.findByBoardTitleContainingOrderByIdDesc("2");
        boardEntityList.forEach(boardEntity -> {
            System.out.println(BoardDTO.toDTO(boardEntity));
        });
    }

    @Test
    @DisplayName("제목으로 검색 내림차순")
    public void searchTestTitleOrWriterDesc() {
        String q = "30"; // 검색어는 하나지만
        List<BoardEntity> boardEntityList = boardRepository.findByBoardTitleContainingOrBoardWriterContainingOrderByIdDesc(q, q); // 검색조건은 2개이다
        boardEntityList.forEach(boardEntity -> {
            System.out.println(BoardDTO.toDTO(boardEntity));
        });
    }

    @Test
    @Transactional
    @DisplayName("검색 결과 페이징")
    public void SearchPaging() {
        String q = "2";
        int page = 0;
        int pageLimit = 10;
        Page<BoardEntity> boardEntities = boardRepository.findByBoardWriterContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        Page<BoardDTO> boardList = boardEntities.map(boardEntity ->
                BoardDTO.builder()
                        // 목록에서 보여줄 것들만 담아가자
                        .id(boardEntity.getId())
                        .boardTitle(boardEntity.getBoardTitle())
                        .boardWriter(boardEntity.getBoardWriter())
                        .createdAt(UtlClass.dataFormat(boardEntity.getCreatedAt()))
                        .boardHits(boardEntity.getBoardHits())
                        .build()
        );
        System.out.println("boardEntities.getContent() = " + boardList.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardEntities.getTotalElements() = " + boardList.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardList.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardEntities.getTotalPages() = " + boardList.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardList.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardEntities.hasPrevious() = " + boardList.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardList.isFirst()); // 첫페이지인지 여부
        System.out.println("boardEntities.isLast() = " + boardList.isLast()); // 마지막페이지인지 여부

    }






}
