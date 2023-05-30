package com.example.book.controller;


import com.example.book.dto.BookDTO;
import com.example.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/save")
    public String saveForm() {
        return "/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BookDTO bookDTO) {
        bookService.save(bookDTO);
        return "index";
    }


}
