package com.example.repository;

import com.example.model.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@Repository
public class CustomBookRepositoryImpl implements CustomBookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> getAvailableBooks() {

        StringBuilder jpql = new StringBuilder();
        jpql.append(
//                "Select NEW com.example.model.Book(b.title, b.author, b.genre, b.publisher) " +
//                "from Book b Left Join UserHasBook u on b.bookId = u.bookId " +
                "SELECT DISTINCT b FROM Book b LEFT JOIN b.userHasBook uhb" +
                        " Where uhb.borrowedTo < CURRENT_DATE OR uhb.book IS NULL");

        TypedQuery<Book> query = entityManager.createQuery(jpql.toString(), Book.class);
        return query.getResultList();
    }

    public List<Book> getCustomBooksByUser(Integer userId, LocalDate fromDate, LocalDate toDate) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT b FROM UserHasBook uhb INNER JOIN uhb.book b" +
                " where uhb.user.userId = ?1 and uhb.borrowedTo >= ?2 and uhb.borrowedTo <= ?3");


        TypedQuery<Book> query = entityManager.createQuery(jpql.toString(), Book.class);
        query.setParameter(1, userId);
        query.setParameter(2, fromDate);
        query.setParameter(3, toDate);

        return query.getResultList();
    }

}