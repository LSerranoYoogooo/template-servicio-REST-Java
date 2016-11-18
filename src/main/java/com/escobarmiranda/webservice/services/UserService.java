package com.escobarmiranda.webservice.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.escobarmiranda.webservice.models.User;
import com.escobarmiranda.webservice.utils.HibernateUtil;

public class UserService {
    private static volatile UserService instance = null;
    private static final Log LOGGER = LogFactory.getLog(UserService.class);

    private UserService() {
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public List<User> getAllUsers() {
        return getAllUsers(0, 0);
    }

    public List<User> getAllUsers(int firstResult, int maxResult) {
        List<User> users = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        if (transaction.getStatus().equals(TransactionStatus.NOT_ACTIVE))
            LOGGER.debug(" >>> Transaction close.");
        Query query = session.createQuery("from User");
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        @SuppressWarnings("unchecked")
        List<User> allUsers = query.list();
        transaction.commit();
        for (Object userObject : allUsers) {
            User user = (User) userObject;
            users.add(user);
        }
        return users;
    }

    public User getUser(int id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        transaction.begin();
        User user = (User) session.get(User.class, id);
        transaction.commit();
        return user;
    }

    public void saveOrUpdateUser(User user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(user);
        transaction.commit();
    }

    public User deleteUser(int id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        User user = getUser(id);
        if (user != null) {
            if (!session.isOpen()) {
                LOGGER.debug(" >>> Session close.");
                LOGGER.debug(" >>> Reopening session.");
                session = HibernateUtil.getSessionFactory().openSession();
            }
            Transaction transaction = session.getTransaction();
            transaction.begin();
            if (transaction.getStatus().equals(TransactionStatus.NOT_ACTIVE))
                LOGGER.debug(" >>> Transaction close.");
            session.delete(user);
            transaction.commit();
        }
        return user;
    }
}
