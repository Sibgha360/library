package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_has_book")
public class UserHasBook {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "user_has_book_id")
  private Integer userHasBookId;

  @Column(name = "borrowed_from")
  private LocalDate borrowedFrom;

  @Column(name = "borrowed_to")
  private LocalDate borrowedTo;

  @Column(name = "creation_time")
  private LocalDateTime creationTime;

  @ManyToOne
  @JoinColumn(name = "book_id")
  @JsonIgnore
  Book book;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonIgnore
  User user;

  public UserHasBook() {
  }

  public UserHasBook(Book book, User user, LocalDate borrowedFrom, LocalDate borrowedTo) {
    this.book = book;
    this.user = user;
    this.borrowedFrom = borrowedFrom;
    this.borrowedTo = borrowedTo;
    this.creationTime = LocalDateTime.now();
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Integer getUserHasBookId() {
    return userHasBookId;
  }

  public void setUserHasBookId(Integer userHasBookId) {
    this.userHasBookId = userHasBookId;
  }

  public LocalDate getBorrowedFrom() {
    return borrowedFrom;
  }

  public void setBorrowedFrom(LocalDate borrowedFrom) {
    this.borrowedFrom = borrowedFrom;
  }

  public LocalDate getBorrowedTo() {
    return borrowedTo;
  }

  public void setBorrowedTo(LocalDate borrowedTo) {
    this.borrowedTo = borrowedTo;
  }

  public LocalDateTime getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(LocalDateTime creationTime) {
    this.creationTime = creationTime;
  }

  @Override
  public String toString() {
    return "UserHasBook{" +
            "bookId=" + book.toString() +
            ", userId='" + user.toString() + '\'' +
            ", userHasBookId='" + userHasBookId + '\'' +
            ", borrowedFrom=" + borrowedFrom +
            ", borrowedTo=" + borrowedTo +
            ", creationTime=" + creationTime +
            '}';
  }
}
