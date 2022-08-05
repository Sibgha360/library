package com.example.repository;

import com.example.model.Book;

import java.time.LocalDate;
import java.util.List;

public interface CustomBookRepository {
    List<Book> getAvailableBooks();

    //d) returns all books borrowed by a given user in a given date range
    List<Book> getCustomBooksByUser(Integer userId, LocalDate fromDate, LocalDate toDate);
}