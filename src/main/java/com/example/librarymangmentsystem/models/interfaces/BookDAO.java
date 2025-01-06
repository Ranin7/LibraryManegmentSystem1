package com.example.librarymangmentsystem.models.interfaces;

import com.example.librarymangmentsystem.models.Book;

import java.util.List;

public interface BookDAO {

    public int save(Book books);
    public void update(Book books);
    public List<Book> getAll();

}