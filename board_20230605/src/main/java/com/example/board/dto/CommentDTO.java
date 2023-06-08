package com.example.board.dto;

import com.example.board.entity.BoardEntity;
import com.example.board.entity.CommentEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentDTO toDTO(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setBoardId(commentEntity.getBoardEntity().getId());  // <-- 여기
        commentDTO.setCreatedAt(commentEntity.getCreatedAt());
        commentDTO.setUpdatedAt(commentEntity.getUpdatedAt());
        return commentDTO;
    }
}
