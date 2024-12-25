package com.example.librarymangmentsystem.models.services;

import com.example.librarymangmentsystem.models.Books;
import com.example.librarymangmentsystem.models.interfaces.BookDOA;
import com.example.librarymangmentsystem.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class BookDOAImp implements BookDOA {

    private HibernateUtil hibernateUtil;
    private SessionFactory sessionFactory;

    public BookDOAImp() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    @Override
    public int save(Books book) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(book);
        session.getTransaction().commit();
        session.close();
        return 1;  // Return an integer as per your original logic (e.g., indicating success)
    }

    @Override
    public void update(Books book) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(book);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Books book = session.get(Books.class, id);
        if (book != null) {
            session.delete(book);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Books getBook(int id) {
        Session session = sessionFactory.openSession();
        return session.get(Books.class, id);
    }

    @Override
    public List<Books> getAll() {
        List<Books> books = null;
        try (Session session = sessionFactory.openSession()) {
            books = session.createQuery("FROM Books", Books.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

 /*   @Override
    public Books findBook(Books book) {
        Session session = sessionFactory.openSession();
        // Use criteria or HQL to find the book
        return session.createQuery("FROM Books WHERE title = :title", Books.class)
                .setParameter("title", book.getTitle())  // Assuming you're searching by title or another field
                .uniqueResult();
    }*/
}
