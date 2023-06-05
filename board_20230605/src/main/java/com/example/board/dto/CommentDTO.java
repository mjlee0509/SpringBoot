package com.example.board.dto;

import lombok.Data;

@Data
public class CommentDTO {

    private Long id;

    private String commentWriter;
    private String commentContents;
    private String boardId;
}
