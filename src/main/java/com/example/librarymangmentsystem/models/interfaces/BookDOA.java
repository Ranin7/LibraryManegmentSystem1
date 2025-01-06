package com.example.librarymangmentsystem.models.interfaces;

import com.example.librarymangmentsystem.models.Book;

import java.util.List;

public interface BookDOA {

    public int save(Book books);
    public void update(Book books);
    public void delete(int id);
    public Book getBook(int id);
    public List<Book> getAll();

}