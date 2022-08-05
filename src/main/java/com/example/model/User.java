package com.example.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "firstName")
  private String firstName;

  @Column(name = "lastName")
  private String lastName;

  @Column(name = "membership_date")
  private LocalDate membershipDate;

  @Column(name = "membership_expiry_date")
  private LocalDate membershipExpiryDate;

  @Column(name = "gender")
  @Enumerated(EnumType.ORDINAL)
  private GenderEnum gender;

  @Column(name = "creation_time")
  private LocalDate creationTIme;

  @OneToMany(mappedBy = "user")
  List<UserHasBook> userHasBook;

  public User() {

  }

  public User(String firstName, String lastName, LocalDate membershipDate, LocalDate membershipExpiryDate, GenderEnum gender) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.membershipDate = membershipDate;
    this.membershipExpiryDate = membershipExpiryDate;
    this.gender = gender;
    this.creationTIme = LocalDate.now();
  }

  @Override
  public String toString() {
    return "User{" +
            "userId=" + userId +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", membershipDate='" + membershipDate + '\'' +
            ", membershipExpiryDate='" + membershipExpiryDate + '}';
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LocalDate getMembershipDate() {
    return membershipDate;
  }

  public void setMembershipDate(LocalDate membershipDate) {
    this.membershipDate = membershipDate;
  }

  public LocalDate getMembershipExpiryDate() {
    return membershipExpiryDate;
  }

  public void setMembershipExpiryDate(LocalDate membershipExpiryDate) {
    this.membershipExpiryDate = membershipExpiryDate;
  }

  public GenderEnum getGender() {
    return gender;
  }

  public void setGender(GenderEnum gender) {
    this.gender = gender;
  }

  public LocalDate getCreationTIme() {
    return creationTIme;
  }

  public void setCreationTIme(LocalDate creationTIme) {
    this.creationTIme = creationTIme;
  }
}
