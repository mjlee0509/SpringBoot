package com.example.board.entity;

import com.example.board.dto.BoardDTO;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "board_table")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String boardWriter;

    @Column(length = 50, nullable = false)
    private String boardTitle;

    @Column(length = 20, nullable = false)
    private String boardPass;

    @Column(length = 500, nullable = false)
    private String boardContents;

    @Column
    private int boardHits;

    @CreationTimestamp  // 어떠한 entity가 insert될 때 동작됨
    @Column(updatable = false)  // 수정할 때 작성시간이 업데이트되지 않게 해주는 Annotation
    private LocalDateTime createdAt;

    public static BoardEntity toSaveEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);    // 조회수 default값을 0으로
        // Entity로 변환할 때에는 createdAt에 대한 column을 따로 줄 필요는 없음 (자동으로 생성)
        return boardEntity;
    }
}
