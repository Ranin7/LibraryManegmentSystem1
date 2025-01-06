package com.example.librarymangmentsystem.models.services;

import com.example.librarymangmentsystem.models.Book;
import com.example.librarymangmentsystem.models.Reservation;
import com.example.librarymangmentsystem.models.User;
import com.example.librarymangmentsystem.models.interfaces.ResDAO;
import com.example.librarymangmentsystem.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import java.util.List;

public class ResDAOImp implements ResDAO {

    private HibernateUtil hibernateUtil;
    private SessionFactory sessionFactory;

    public ResDAOImp() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    @Override
    public void update(Reservation res) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(res);
        session.getTransaction().commit();
        session.close();
    }
    public boolean updateBookStatus(Book book) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.update(book);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }
    public List<Reservation> getReservationsByBookId(int bookId) {
        Session session = sessionFactory.openSession();
        List<Reservation> reservations = session.createQuery("FROM Reservation WHERE bookid.id = :bookId", Reservation.class)
                .setParameter("bookId", bookId)
                .getResultList();
        session.close();
        return reservations;
    }




    @Override
    public List<Reservation> getAll() {
        List<Reservation> res = null;
        try (Session session = sessionFactory.openSession()) {
            res = session.createQuery("FROM Reservation",Reservation.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }



    @Override
    public boolean saveReservation(Reservation reservation) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(reservation);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public Book getBookById(int bookId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Book.class, bookId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserById(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}