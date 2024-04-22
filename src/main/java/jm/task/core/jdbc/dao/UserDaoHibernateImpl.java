package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        Session session = Util.openSession();
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS User ("
                    + "id BIGINT PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(50),"
                    + "lastName VARCHAR(50),"
                    + "age TINYINT)").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            Util.closeSession(session);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        Session session = Util.openSession();
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            Util.closeSession(session);
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        Session session = Util.openSession();
        try {
            transaction = session.beginTransaction();
            String sql = "INSERT INTO User (name, lastName, age) VALUES (:name, :lastName, :age)";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("name", name);
            query.setParameter("lastName", lastName);
            query.setParameter("age", age);
            query.executeUpdate();
            transaction.commit();
            System.out.println("User " + name + " " + lastName + " has been added to the database");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            Util.closeSession(session);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        Session session = Util.openSession();
        try {
            transaction = session.beginTransaction();
            String sql = "DELETE FROM User WHERE id = :id";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
            System.out.println("User with id " + id + " has been removed from the database");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            Util.closeSession(session);
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.openSession();
        Transaction transaction = null;
        List<User> users = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            users = session.createQuery("FROM User", User.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            Util.closeSession(session);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        Session session = Util.openSession();
        try {
            transaction = session.beginTransaction();
            String sql = "TRUNCATE TABLE User";
            SQLQuery query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
            System.out.println("Users table has been cleaned");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            Util.closeSession(session);
        }
    }

}
