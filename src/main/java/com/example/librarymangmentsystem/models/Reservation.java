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

        @ManyToOne
        @JoinColumn(name = "bid", nullable = false)
        public Books bookid;

        @ManyToOne
        @JoinColumn(name="uid" ,nullable = false)
        public User useri;


    public Reservation() {
        super();

    }
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

    public Books getBookid() {
        return bookid;
    }

    public void setBookid(Books bookid) {
        this.bookid = bookid;
    }

    public User getUseri() {
        return useri;
    }


    public void setUseri(User useri) {
        this.useri = useri;
    }
}
