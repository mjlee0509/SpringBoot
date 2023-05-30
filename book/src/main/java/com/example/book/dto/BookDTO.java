package com.example.book.dto;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String bookName;
    private String bookAuthor;
    private int bookPrice;
}
