package com.example.librarymangmentsystem.models;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Column(name = "BookName")
    private String bookName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Author")
    private String author;

    @Lob
    @Column(name = "Image", columnDefinition = "BLOB")
    private byte[] image;

    @Column(name = "Genre")
    private String genre;

    @Column(name = "Available")
    private String available;

    @Column(name = "PublicationYear")
    private String publicationYear;

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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
    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }


}