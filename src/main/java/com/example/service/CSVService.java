package com.example.service;

import java.util.List;

import com.example.helper.CSVHelper;
import com.example.model.Book;
import com.example.model.User;
import com.example.model.UserHasBook;
import com.example.repository.BookRepository;
import com.example.repository.UserHasBookRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserHasBookRepository userHasBookRepository;

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    public void saveBooks(MultipartFile file) throws Exception {
        List<Book> books = CSVHelper.csvToBooks(file.getInputStream());
        bookRepository.saveAll(books);
    }

    public void saveUsers(MultipartFile file) throws Exception {
        List<User> users = CSVHelper.csvToUsers(file.getInputStream());
        userRepository.saveAll(users);
    }

    public void saveBorrowed(MultipartFile file) throws Exception {
        List<UserHasBook> usersHasBooks = CSVHelper.csvToUserHasBook(file.getInputStream(), userService, bookService);
        userHasBookRepository.saveAll(usersHasBooks);
    }
}
