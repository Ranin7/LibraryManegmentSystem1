package com.example.librarymangmentsystem.models;

import javax.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "BookName")
    private String bookName;

    @Column(name = "ResDate")
    private String ResDate;

    @Column(name = "ReturnDate")
    private String ReturnDate;

    @Column(name = "UName")
    private String UName;

    @Column(name = "AnotherB")
    private String AnotherB;
    @Column(name = "available")
    private String available;


    @ManyToOne
    @JoinColumn(name = "bid" )
    public Book bookid;

    @ManyToOne
    @JoinColumn(name="uid")
    public User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getResDate() {
        return ResDate;
    }

    public void setResDate(String resDate) {
        ResDate = resDate;
    }

    public String getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(String returnDate) {
        ReturnDate = returnDate;
    }

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public String getAnotherB() {
        return AnotherB;
    }

    public void setAnotherB(String anotherB) {
        AnotherB = anotherB;
    }

    public Book getBookid() {
        return bookid;
    }

    public void setBookid(Book bookid) {
        this.bookid = bookid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }
}