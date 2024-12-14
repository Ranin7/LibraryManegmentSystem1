package com.example.librarymangmentsystem.models;
import javax.persistence.*;

@Entity
@Table(name="Books")
public class Books {

    @Column(name="Book Name")
    private String bookName;

    @Id
    @GeneratedValue
    private int id;

    @Column(name="Author")
    private String author;

    @Column(name="Genre")
    private String genre;

    @Column(name="Available")
    private String available;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    @Column(name="Publication Year")
    private String publicationYear;


}
