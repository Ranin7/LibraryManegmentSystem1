package com.example.librarymangmentsystem.models.services;

import com.example.librarymangmentsystem.models.User;
import com.example.librarymangmentsystem.models.Role;
import com.example.librarymangmentsystem.models.interfaces.UserDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.example.librarymangmentsystem.util.HibernateUtil;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public void addUser(User user) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            if (user.getRole() != null && user.getRole().getId() == 0) {
                session.save(user.getRole());
            }

            session.save(user);
            transaction.commit();
            System.out.println("User added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            String hql = "FROM User WHERE username = :username";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            String hql = "FROM User WHERE email = :email";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            String hql = "UPDATE User SET password = :password WHERE email = :email";
            Query query = session.createQuery(hql);
            query.setParameter("password", newPassword);
            query.setParameter("email", email);
            int result = query.executeUpdate();
            transaction.commit();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean emailExists(String email) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(*) FROM User WHERE email = :email";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("email", email.trim());  // التأكد من تنظيف البريد الإلكتروني
            long count = query.uniqueResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean usernameExists(String username) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(*) FROM User WHERE username = :username";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("username", username);
            long count = query.uniqueResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            String hql = "FROM User WHERE role.id = :roleId";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("roleId", role);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
