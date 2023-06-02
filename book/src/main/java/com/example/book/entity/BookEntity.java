package com.example.book.entity;

import com.example.book.dto.BookDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "book_table")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String bookName;

    @Column(length = 20, nullable = false)
    private String bookAuthor;

    @Column
    private int bookPrice;

    public static BookEntity toSaveEntity(BookDTO bookDTO) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookName(bookDTO.getBookName());
        bookEntity.setBookAuthor(bookDTO.getBookAuthor());
        bookEntity.setBookPrice(bookDTO.getBookPrice());
        return bookEntity;
    }


    public static BookEntity toUpdateEntity(BookDTO bookDTO) {
        BookEntity bookEntity = new BookEntity();
        // 주의 1. 업데이트 떄에는 반드시 id를 넘겨줘야 함
        bookEntity.setId(bookDTO.getId());
        // 주의 2. 반드시 모든 필드값을 넘겨줘야 함
        bookEntity.setBookName(bookDTO.getBookName());
        bookEntity.setBookAuthor(bookDTO.getBookAuthor());
        bookEntity.setBookPrice(bookDTO.getBookPrice());
        return bookEntity;

    }
}
