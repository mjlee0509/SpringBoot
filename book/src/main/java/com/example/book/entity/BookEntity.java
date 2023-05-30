package com.example.book.entity;

import com.example.book.dto.BookDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
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
        // 매개변수가 뭔지, 리턴타입이 뭔지,
    }


}
