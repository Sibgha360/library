package com.example.controller;

import com.example.helper.exception.BadRequestException;
import com.example.model.Book;
import com.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api")
public class LibraryController {
    @Autowired
    BookService bookService;

    @GetMapping("/available_books")
    public ResponseEntity<List<Book>> getAvailableBooks() throws Exception {
        List<Book> availableBooks = bookService.findAvailableBooks();
        return ResponseEntity.status(HttpStatus.OK).body(availableBooks);
    }

    @GetMapping("/books_by_borrower")
    public ResponseEntity<List<Book>> getBooksByBorrower(@RequestParam("userId") Integer userId,
                                                     @RequestParam("fromDate") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate fromDate,
                                                     @RequestParam("toDate") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate toDate) throws Exception {
        if (userId == null || userId <= 0 || fromDate.isAfter(toDate)) {
            throw new BadRequestException("Invalid request parameters");
        }

        List<Book> books = bookService.getBooksByUser(userId, fromDate, toDate);
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }
}
