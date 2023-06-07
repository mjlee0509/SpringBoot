package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.BoardFileEntity;
import com.example.board.repo.BoardFileRepository;
import com.example.board.repo.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    public Long save(BoardDTO boardDTO) throws IOException {
//        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
//        boardRepository.save(boardEntity);
//        return boardRepository.save(boardEntity).getId();
        if(boardDTO.getBoardFile().get(0).isEmpty()) {
            // 파일이 없는 경우
            // 기존의 save 메서드를 그대로 유지하되, Entity에 가서 fileAttached 값을 설정해주자
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
            return boardRepository.save(boardEntity).getId();
        } else {
            // 파일이 있는 경우
            // 1. Board 테이블에 데이터 저장
            BoardEntity boardEntity = BoardEntity.toSaveEntityWithFile(boardDTO); // id값이 없음
            BoardEntity savedEntity = boardRepository.save(boardEntity); // id값을 포함

            // 2. 파일이름을 꺼내기; 저장용 이름 만들기; 파일을 로컬에 저장
            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
                // 2-1. 파일이름 꺼내기
                String originalFileName = boardFile.getOriginalFilename();
                // 2-2. 저장용 이름 만들기
                String storedFileName = System.currentTimeMillis() + "-" + originalFileName;
                // 2-3. 파일을 로컬에 저장
                String savePath = "D:\\springBoot_img\\" + storedFileName;
                boardFile.transferTo(new File(savePath));

                // 3. BoardFileEntity로 변환 후 board_file_table에 저장
                /**
                 * (중요!!)자식 데이터를 저장할 때 반드시 부모의 id가 아닌 부모의 Entity 객체가 전달되어야 한다
                 */
                BoardFileEntity boardFileEntity = BoardFileEntity.toSaveBoardFileEntity
                        (savedEntity,
                                originalFileName,
                                storedFileName);
                boardFileRepository.save(boardFileEntity);
            }
            return savedEntity.getId();
        }
    }
    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
//        for (BoardEntity boardEntity : boardEntityList) {
//            boardDTOList.add(BoardDTO.toDTO(boardEntity));
//        }
        boardEntityList.forEach(boardEntity -> {
            boardDTOList.add(BoardDTO.toDTO(boardEntity));
        });
        return boardDTOList;

    }

    @Transactional
    public BoardDTO findById(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return BoardDTO.toDTO(boardEntity);
    }

    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);

    }

    public void update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
    }
}
