package com.hibernate;

import com.hibernate.entity.User;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.*;
import java.util.ArrayList;

public class UserRepository {
    public static final String FIND_BY_ID_QUERY = """
         FROM User
         WHERE id = :id
    """;
    private static final String SELECT_QUERY = """
        FROM User
    """;
    public static final String UPDATE_QUERY = """
        UPDATE users SET username = :username, password = :password, surname = :surname, name = :name
        WHERE id = :id
    """;

    private final SessionFactory sessionFactory;

    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public User findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_BY_ID_QUERY, User.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return (List<User>) session.createQuery(SELECT_QUERY, User.class).list();
        }
    }

    public boolean save(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
            return true;
        }
    }

    public boolean update(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery(UPDATE_QUERY, User.class)
                    .setParameter("username", user.getUsername())
                    .setParameter("password", user.getPassword())
                    .setParameter("surname", user.getSurname())
                    .setParameter("name", user.getName())
                    .setParameter("id", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
            return true;
        }
    }

    public boolean delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.getReference(User.class, id);
            session.remove(user);
            session.getTransaction().commit();
            return true;
        }
    }


}
