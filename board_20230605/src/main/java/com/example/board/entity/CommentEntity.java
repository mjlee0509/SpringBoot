package com.example.board.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "comment_table")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column(length = 200, nullable = false)
    private String commentContents;

    @Column
    private int boardId;
}
