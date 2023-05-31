package com.example.book.service;


import com.example.book.dto.BookDTO;
import com.example.book.entity.BookEntity;
import com.example.book.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Long save(BookDTO bookDTO) {
//        BookEntity bookEntity = new BookEntity();
//        bookEntity.setBookName(bookDTO.getBookName());
//        bookEntity.setBookAuthor(bookDTO.getBookAuthor());
//        bookEntity.setBookPrice(bookDTO.getBookPrice());
//        BookEntity bookEntity = toSaveEntity(bookDTO);
        BookEntity bookEntity = BookEntity.toSaveEntity(bookDTO);
//        System.out.println("bookEntity = " + bookEntity);
//        BookEntity savedEntity = bookRepository.save(bookEntity);
//        System.out.println("savedEntity = " + savedEntity);
        // 저장처리 후 저장한 데이터의 id 리턴
        return bookRepository.save(bookEntity).getId();
    }

//    private BookEntity toSaveEntity(BookDTO bookDTO) {
//        BookEntity bookEntity = new BookEntity();   // Entity에서 기본생성자를 통해 타 클래스에서 이 객체를 만들 수 없게 설정
//        bookEntity.setBookName(bookDTO.getBookName());
//        bookEntity.setBookAuthor(bookDTO.getBookAuthor());
//        bookEntity.setBookPrice(bookDTO.getBookPrice());
//        return bookEntity;
//    }

    //Service와 Repository가 Entity 객체를 주고받는다!
    public List<BookDTO> findAll() {
        List<BookEntity> bookEntityList = bookRepository.findAll();
        List<BookDTO> bookDTOList = new ArrayList<>();
        // Entity 객체를 List로 변환 List<BookEntity> --> List<BookDTO>
            /*
            1. Entity -> DTO 변환 메서드 호출 ( BoardDTO에 정의)
            2. 호출 결과를 DTO에 넣음
            3. DTO 객체를 DTO 리스트에 추가
            4. 반복문 종료 후 DTO 리스트 리턴
              */
        for (BookEntity bookEntity : bookEntityList) {
            BookDTO bookDTO = BookDTO.toDTO(bookEntity);
            bookDTOList.add(bookDTO);
            // bookDTOList.add(BookDTO.toDTO((bookEntity)));  // <-- 위의 2줄을 한 줄로 줄이면
        }
        return bookDTOList;
    }

    public BookDTO findById(Long id) {
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(id);
        if (optionalBookEntity.isPresent()) {
            System.out.println("있");
            // 1. optional 객체에서 꺼내기
            BookEntity bookEntity = optionalBookEntity.get();
            // 2. 이제 Entity를 DTO로 변환하자
            BookDTO bookDTO = BookDTO.toDTO(bookEntity);
            return bookDTO;
            // return BookDTO.toDTO(optionalBookEntity.get());   // <== 위의 3줄을 한 줄로 줄이면
        } else {
            System.out.println("없");
            return null;
        }
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public void update(BookDTO bookDTO) {
        // Spring Data JPA에는 update 기능이 없음. 다만, save 메서드에 **id가 있는 경우** update 처리됨
        // 그러므로 save에서 사용했던 toSaveEntity 메서드도 다시 사용할 수 없음 (id값을 가지고 있기 떄문에)
        BookEntity bookEntity = BookEntity.toUpdateEntity(bookDTO);
        bookRepository.save(bookEntity);
    }
}
