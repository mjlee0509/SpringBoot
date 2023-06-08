package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.CommentDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.CommentEntity;
import com.example.board.repo.BoardRepository;
import com.example.board.repo.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;  // <-- boardRepository도 주입해주자

    public Long save(CommentDTO commentDTO) {
        BoardEntity boardEntity = boardRepository.findById(commentDTO.getBoardId()).orElseThrow(() -> new NoSuchElementException()); //>-- boardId 받아오려면 이렇게
        CommentEntity commentEntity = CommentEntity.toSaveEntity(boardEntity, commentDTO);
        return commentRepository.save(commentEntity).getId();

    }

    @Transactional
    public List<CommentDTO> findAll(Long boardId) {
//        List<CommentEntity> commentEntityList = commentRepository.findAll();
//        List<CommentDTO> commentDTOList = new ArrayList<>();
//
//        commentEntityList.forEach(commentEntity -> {
//            commentDTOList.add(CommentDTO.toDTO(commentEntity, new BoardEntity()));
//        });
//        return commentDTOList;

        // 방법 1. BoardEntity에서 댓글 목록 가져오기
//        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException());
//        List<CommentEntity> commentEntityList = BoardEntity.getCommentEntityList();
        // 방법 2. CommentRepository에서 댓글 목록 가져오기
        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException());
        List<CommentEntity> commentEntityList = commentRepository.findByBoardEntityOrderByIdDesc(boardEntity);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        commentEntityList.forEach(comment -> {
            commentDTOList.add(CommentDTO.toDTO(comment));
        });
        return commentDTOList;


    }

}
