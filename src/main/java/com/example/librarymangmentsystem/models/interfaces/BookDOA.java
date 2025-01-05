package com.example.librarymangmentsystem.models.interfaces;

import com.example.librarymangmentsystem.models.Books;

import java.util.List;

public interface BookDOA {

    public int save(Books book);
    public void update(Books book);
    public void delete(int id);
    public Books getBook(int id);
    public List<Books> getAll();

}