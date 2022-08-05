package com.example.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "book")
public class Book {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "book_id")
  private Integer bookId;

  @Column(name = "title")
  private String title;

  @Column(name = "author")
  private String author;

  @Column(name = "genre")
  private String genre;

  @Column(name = "publisher")
  private String publisher;

  @Column(name = "creation_time")
  private LocalDateTime creationTime;

  @OneToMany(mappedBy = "book")
  List<UserHasBook> userHasBook;

//  @ManyToMany
//  @JoinTable(name = "user_has_book",
//          joinColumns = @JoinColumn(name ="book_id"),
//          inverseJoinColumns =  @JoinColumn(name ="user_i234asdfd"))
//  private List<User> borrowers;



  public Book() {

  }

  public Book(String title, String author, String genre, String publisher) {
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.publisher = publisher;
    this.creationTime = LocalDateTime.now();
  }



  public Integer getBookId() {
    return bookId;
  }

  public void setBookId(Integer bookId) {
    this.bookId = bookId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public List<UserHasBook> getUserHasBook() {
    return userHasBook;
  }

  public void setUserHasBook(List<UserHasBook> userHasBook) {
    this.userHasBook = userHasBook;
  }

  //  public List<User> getBorrowers() {
//    return borrowers;
//  }
//
//  public void setBorrowers(List<User> borrowers) {
//    this.borrowers = borrowers;
//  }

  @Override
  public String toString() {
    return " Book [book id=" + bookId + ", title=" + title + ", author=" + author + ", genre=" + genre + "]";
  }

}
