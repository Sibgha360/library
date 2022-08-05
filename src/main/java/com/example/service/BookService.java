package com.example.service;

import com.example.helper.exception.ResourceNotFoundException;
import com.example.model.Book;
import com.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public Book findByTitle(String title) throws Exception {
        Book book = bookRepository.findByTitle(title);
        if (book == null) {
            throw new ResourceNotFoundException("Book not found with the title: " + title);
        }
        return book;
    }

    public List<Book> findAvailableBooks() throws Exception {
        List<Book> availableBooks = bookRepository.getAvailableBooks();

        if (availableBooks == null || availableBooks.isEmpty()) {
            throw new ResourceNotFoundException("No Available Books exist");
        }

        return availableBooks;
    }

    public List<Book> getBooksByUser(Integer userId, LocalDate fromDate, LocalDate toDate) throws Exception {
        List<Book> booksByUser = bookRepository.getCustomBooksByUser(userId, fromDate, toDate);

        if (booksByUser == null || booksByUser.isEmpty()) {
            throw new ResourceNotFoundException("Books not found for user ID: " + userId + ", that were borrowed from " + fromDate.toString() + "to " + toDate.toString());
        }

        return booksByUser;
    }
}
