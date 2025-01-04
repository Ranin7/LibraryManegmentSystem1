package com.example.librarymangmentsystem.models.services;

import com.example.librarymangmentsystem.models.Books;
import com.example.librarymangmentsystem.models.Reservation;
import com.example.librarymangmentsystem.models.Role;
import com.example.librarymangmentsystem.models.interfaces.ResDAO;
import com.example.librarymangmentsystem.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
        // Implement logic to update reservation if needed
    }

    @Override
    public Reservation getRes(int id) {
        // Implement logic to get reservation by id if needed
        return null;
    }

    @Override
    public List<Reservation> getAll() {
        // Implement logic to retrieve all reservations if needed
        return List.of();
    }

    @Override
    public boolean saveReservation(Reservation reservation) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(reservation); // Save the reservation
            transaction.commit(); // Commit the transaction
            return true; // Successfully saved
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback in case of error
            }
            e.printStackTrace();
            return false; // Failed to save
        }
    }
    // Fetch the book by ID
    public Books getBookById(int bookId) {
        Session session = sessionFactory.openSession();
        return session.get(Books.class, bookId);
    }

    // Fetch the user by ID
    public Role.User getUserById(int userId) {
        Session session = sessionFactory.openSession();
        return session.get(Role.User.class, userId);
    }
}
