package com.example.repository;

import com.example.repository.CustomBookRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>, CustomBookRepository {
    Book findByTitle(String title);
}
