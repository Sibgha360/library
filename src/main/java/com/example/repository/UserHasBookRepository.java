package com.example.repository;

import com.example.model.UserHasBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHasBookRepository extends JpaRepository<UserHasBook, Long> {
}
