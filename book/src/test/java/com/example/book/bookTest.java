package com.example.book;

import com.example.book.dto.BookDTO;
import com.example.book.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

// Assertions 클래스가 가지고 있는 모든 static 메서드를 가져오겠다.

import java.awt.print.Book;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class bookTest {
    @Autowired
    private BookService bookService;

    // 1. 등록 테스트
    /**
        1-1. 신규 도서 데이터 생성
        1-2. save 메서드를 호출하여 저장 처리
        1-3. 저장한 데이터의 id값을 가져와서
        1-4. 해당 id로 DB에서 조회
            1-4-1. 1-1에서 만든 객체의 책제목 값과 1-3-1에서 조회한 객체의 책제목 값이 일치하는지 판단
            1-4-2. 일치하면 테스트 성공, 일치하지 않으면 테스트 실패
     **/

    private BookDTO newBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookName("test book");
        bookDTO.setBookAuthor("test author");
        bookDTO.setBookPrice(10000);
        return bookDTO;
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void bookSaveTest() {
        BookDTO bookDTO = newBook(); // 테스트 데이터 준비
        Long saveId = bookService.save(bookDTO); // 저장을 위해 메서드 호출 후 id 값 가져오기
        // id로 조회
        BookDTO findDTO = bookService.findById(saveId);
        // 테스트용 데이터의 제목과 조회한 데이터의 제목 일치 여부 확인 (먼저, 클래스 위의 import 라인 확인)
        assertThat(bookDTO.getBookName()).isEqualTo(findDTO.getBookName());

    }

    // 2. 삭제 테스트
    @Test
    @Transactional
    @Rollback
    @DisplayName("삭제 테스트")
    public void bookDeleteTest() {
        /**
         * 2-1. 새로운 데이터 저장
         * 2-2. 저장된 데이터의 id를 가져옴
         * 2-3. 해당 id로 삭제 처리
         * 2-4. 해당 id로 조회했을 때 null이면 테스트 성공
         */
        BookDTO bookDTO = newBook();
        Long saveId = bookService.save(bookDTO);
        bookService.delete(saveId);
        assertThat(bookService.findById(saveId)).isNull();
    }

    // 3. 수정 테스트
    @Test
    @Transactional
    @Rollback
    @DisplayName("수정 테스트")
    public void bookUpdateTest() {
        /**
         * 3-1. 새로운 데이터 저장
         * 3-2. 수정할 데이터 준비 및 수정 처리 (제목만 변경해보자)
         * 3-3. 데이터 조회
         * 3-4. 2번에서 수정한 제목과 3번에서 조회한 제목이 일치하면 수정 성공
         */
        // 3-1.
        BookDTO bookDTO = newBook();
        Long saveId = bookService.save(bookDTO);
        // 3-2.
        bookDTO.setId(saveId);
        bookDTO.setBookName("수정된 제목");
        bookService.update(bookDTO);
        // 3-3.
        BookDTO dto = bookService.findById(saveId);
        // 3-4.
        assertThat(dto.getBookName()).isEqualTo(bookDTO.getBookName());

    }




}
