package com.example.book;

import com.example.book.dto.BookDTO;
import com.example.book.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// Assertions 클래스가 가지고 있는 모든 static 메서드를 가져오겠다.
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class bookTest {
    @Autowired
    private BookService bookService;

    // 1. 도서 등록
    /*
        1-1. 신규 도서 데이터 생성
        1-2. save 메서드를 호출하여 저장 처리
        1-3. 저장한 데이터의 id값을 가져와서
        1-4. 해당 id로 DB에서 조회
            1-4-1. 1-1에서 만든 객체의 책제목 값과 1-3-1에서 조회한 객체의 책제목 값이 일치하는지 판단
            1-4-2. 일치하면 테스트 성공, 일치하지 않으면 테스트 실패
     */

    private BookDTO newBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookName("test book");
        bookDTO.setBookAuthor("test author");
        bookDTO.setBookPrice(10000);
        return bookDTO;
    }

    @Test
    public void bookSaveTest() {
        BookDTO bookDTO = newBook(); // 테스트 데이터 준비
        Long saveId = bookService.save(bookDTO); // 저장을 위해 메서드 호출 후 id 값 가져오기
        // id로 조회
        BookDTO findDTO = bookService.findById(saveId);
        // 테스트용 데이터의 제목과 조회한 데이터의 제목 일치 여부 확인 (먼저, 클래스 위의 import 라인 확인)
        assertThat(bookDTO.getBookName()).isEqualTo(findDTO.getBookName());

    }



}
