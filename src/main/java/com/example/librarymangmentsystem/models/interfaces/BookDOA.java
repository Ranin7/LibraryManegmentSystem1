package com.example.librarymangmentsystem.models.interfaces;

import com.example.librarymangmentsystem.models.Books;

import java.util.List;

public interface BookDOA {

    public int save(Books books);
    public void update(Books books);
    public void delete(int id);
    public Books getBook(int id);
    public List<Books> getAll();
    //public Books findBook(Books book);
}
