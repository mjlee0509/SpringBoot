package com.example.book.controller;


import com.example.book.dto.BookDTO;
import com.example.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.awt.print.Book;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BookDTO bookDTO) {
        bookService.save(bookDTO);
        return "index";
    }

    @GetMapping("/books")
    public String findAll(Model model) {
        List<BookDTO> bookDTOList = bookService.findAll();
        model.addAttribute("bookList", bookDTOList);
        return "books";
    }

    @GetMapping("/book/{id}")
    public String findById(@PathVariable Long id, Model model) {
        BookDTO bookDTO = bookService.findById(id);
        model.addAttribute("book", bookDTO);
        return "detail";
    }

    @GetMapping("/book/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/book/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BookDTO bookDTO = bookService.findById(id);
        model.addAttribute("book", bookDTO);
        return "update";
    }

    @PostMapping("/book/update")
    public String update(@ModelAttribute BookDTO bookDTO) {
        bookService.update(bookDTO);
        return "redirect:/books";
    }








}
