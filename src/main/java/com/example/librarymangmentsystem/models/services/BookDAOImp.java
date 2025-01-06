package com.example.librarymangmentsystem.models.services;

import com.example.librarymangmentsystem.models.Book;
import com.example.librarymangmentsystem.models.interfaces.BookDAO;
import com.example.librarymangmentsystem.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;



import java.util.List;

public class BookDAOImp implements BookDAO {

    private HibernateUtil hibernateUtil;
    private SessionFactory sessionFactory;

    public BookDAOImp() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    @Override
    public int save(Book books) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(books);
        session.getTransaction().commit();
        session.close();
        return 1;
    }

    @Override
    public void update(Book books) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(books);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Book books = session.get(Book.class, id);
        if (books != null) {
            session.delete(books);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Book getBook(int id) {
        Session session = sessionFactory.openSession();
        return session.get(Book.class, id);
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = null;
        try (Session session = sessionFactory.openSession()) {
            books = session.createQuery("FROM Book", Book.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

}