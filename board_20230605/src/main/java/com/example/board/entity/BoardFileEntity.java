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

    @Column
    private int boardId;

}
