package com.example.board.dto;

import com.example.board.entity.BoardEntity;
import com.example.board.entity.BoardFileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardPass;
    private String boardContents;
    private int boardHits;

    private List<MultipartFile> boardFile;
    private int fileAttached;
    private String createdAt;  // 1. String으로 바꾼다
    private List<String> originalFileName;
    private List<String> storedFileName;


    public static BoardDTO toDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setCreatedAt(String.valueOf(boardEntity.getCreatedAt()));

        // 파일 여부에 따른 코드 추가
        if (boardEntity.getFileAttached() == 1) {
            boardDTO.setFileAttached(1);
            // 파일 이름을 담을 리스트 객체 선언
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();

            // 첨부파일에 각각 접근
            for (BoardFileEntity boardFileEntity : boardEntity.getBoardFileEntityList()) {
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                storedFileNameList.add(boardFileEntity.getStoredFileName());
            }
            boardDTO.setOriginalFileName(originalFileNameList);
            boardDTO.setStoredFileName(storedFileNameList);

        } else {
            boardDTO.setFileAttached(0);
        }

        return boardDTO;
        // Build 패턴 (써도 그만 안써도 그만; 요즘 많이 쓰긴 함)
//        return BoardDTO.builder()
//                .id(boardEntity.getId())
//                .boardTitle(boardEntity.getBoardTitle())
//                .boardWriter(boardEntity.getBoardWriter())
//                .boardPass(boardEntity.getBoardPass())
//                .boardContents(boardEntity.getBoardContents())
//                .boardHits(boardEntity.getBoardHits())
//                .createdAt(boardEntity.getCreatedAt())
//                .build();
    }
}
