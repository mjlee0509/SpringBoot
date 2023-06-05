package com.example.board.dto;

import com.example.board.entity.BoardEntity;
import lombok.Data;

@Data
public class BoardDTO {

    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardPass;
    private String boardContents;
    private int boardHits;


    public static BoardDTO toDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        return boardDTO;
    }
}
