package com.bookstore.app.entities.user.persistance;


import com.bookstore.app.entities.user.User;
import com.bookstore.app.exceptions.QueryException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.data.relational.core.query.Criteria;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsersRepository implements IUsersRepository {
    private static SessionFactory factory;
    public UsersRepository() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public User saveUser(User user) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e.getMessage());
        }
        return user;
    }

    @Override
    public User findUserById(UUID id) {
        try (Session session = factory.openSession()) {
            var user = (User) session.get(User.class, id);
            if (user == null) throw new QueryException("No such user");
            return user;
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
        throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<User> findUserByCriteria(FindCriteria criteria) {
        List<User> listOfUsers = new ArrayList<>();
        if (criteria.getPhoneNumber() != null) {
            var user = findUserByPhoneNumber(criteria.getPhoneNumber());
            if (user != null) listOfUsers.add(user);
        }
        if (criteria.getBirthday() != null) listOfUsers = findUserByBirthday(criteria.getBirthday());
        return listOfUsers;
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("from User u where u.phoneNumber = :phoneNumber");
            query.setParameter("phoneNumber", phoneNumber);
            var user = query.list();
            if (user.isEmpty()) throw new QueryException("No such user");
            return (User) user.getFirst();
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<User> findUserByBirthday(LocalDate date) {
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("from User u where u.birthday = :birthday");
            query.setParameter("birthday", date);
            var users = query.list();
            if (users == null) throw new QueryException("No such user");
            return users;
        } catch (QueryException e) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public User updateUser(User user) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(user);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return user;
    }

    @Override
    public void deleteUserById(UUID id) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            User user = (User)session.get(User.class, id);
            session.delete(user);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
