package com.example.board.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "board_file_table")
public class BoardFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String originalFileName;

    @Column(length = 100, nullable = false)
    private String storedFileName;


    // fk 지정은 어떻게 하는걸까?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id") // db에 생성되는 참조 칼럼의 이름
    private BoardEntity boardEntity;    // 여기에는 무조건 부모 Entity 타입으로 정의

    public static BoardFileEntity toSaveBoardFileEntity(BoardEntity savedEntity, String originalFileName, String storedFileName) {
        BoardFileEntity boardFileEntity = new BoardFileEntity();
        boardFileEntity.setBoardEntity(savedEntity);
        boardFileEntity.setOriginalFileName(originalFileName);
        boardFileEntity.setStoredFileName(storedFileName);
        return boardFileEntity;
    }


}
