package com.example.book.service;


import com.example.book.dto.BookDTO;
import com.example.book.entity.BookEntity;
import com.example.book.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public void save(BookDTO bookDTO) {
//        BookEntity bookEntity = new BookEntity();
//        bookEntity.setBookName(bookDTO.getBookName());
//        bookEntity.setBookAuthor(bookDTO.getBookAuthor());
//        bookEntity.setBookPrice(bookDTO.getBookPrice());
//        BookEntity bookEntity = toSaveEntity(bookDTO);
        BookEntity bookEntity = BookEntity.toSaveEntity(bookDTO);
        bookRepository.save(bookEntity);
    }
//    private BookEntity toSaveEntity(BookDTO bookDTO) {
//        BookEntity bookEntity = new BookEntity();   // Entity에서 기본생성자를 통해 타 클래스에서 이 객체를 만들 수 없게 설정
//        bookEntity.setBookName(bookDTO.getBookName());
//        bookEntity.setBookAuthor(bookDTO.getBookAuthor());
//        bookEntity.setBookPrice(bookDTO.getBookPrice());
//        return bookEntity;
//    }
}
