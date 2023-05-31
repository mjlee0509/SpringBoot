package com.example.book.dto;

import com.example.book.entity.BookEntity;
import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String bookName;
    private String bookAuthor;
    private int bookPrice;

//    public static BookDTO toFindAllDTO(BookEntity bookEntity) {
//        BookDTO bookDTO = new BookDTO();
//        bookDTO.setBookName(bookEntity.getBookName());
//        bookDTO.setBookAuthor(bookEntity.getBookAuthor());
//        bookDTO.setBookPrice(bookEntity.getBookPrice());
//        return bookDTO;
//    }

    public static BookDTO toDTO(BookEntity bookEntity) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookEntity.getId());
        bookDTO.setBookName(bookEntity.getBookName());
        bookDTO.setBookAuthor(bookEntity.getBookAuthor());
        bookDTO.setBookPrice(bookEntity.getBookPrice());
        return bookDTO;
    }
}
