package com.example.board.repo;

import com.example.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // update board_table set board_hits = board_hits+1 where id=?; 를 만들어야 하는데...
    /**
     * 1. 이걸 jpa의 쿼리 자동완성 기능으로 만들기는 어렵다
     * 2. 그래서 jpql이란걸 써보기로 했다.
     * 3. JPQL(Java Persistance Query Language) :: 필요한 쿼리를 직접 만들어서 적용하고자 할 때
     */

    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
//    @Query(value = "update board_table b set b.boardHits=b.boardHits+1 where b.id=:id", nativeQuery = true)
    /**
     * 주의사항 1. 접근하고자 하는 table이 아닌 Entity로 접근해줘야 한다
     * 주의사항 2. 약칭을 써주는 게 국룰이다 (ex. BoardEntity b, b.boardHits, b.id 등등)
     */
    void updateHits(@Param("id") Long id);
}
