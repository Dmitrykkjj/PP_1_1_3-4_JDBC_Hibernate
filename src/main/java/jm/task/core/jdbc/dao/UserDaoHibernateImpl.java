package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (SessionFactory sessionFactory = Util.provideSessionFactory()) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            transaction.commit();
            System.out.println("Users table created");
        } catch (Exception e) {
            System.err.println("Error occurred while creating users table: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try (SessionFactory sessionFactory = Util.provideSessionFactory()) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
            transaction.commit();
            System.out.println("Users table dropped");
        } catch (Exception e) {
            System.err.println("Error occurred while dropping users table: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (SessionFactory sessionFactory = Util.provideSessionFactory()) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User with name " + name + " saved");
        } catch (Exception e) {
            System.err.println("Error occurred while saving user: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (SessionFactory sessionFactory = Util.provideSessionFactory()) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
            } else {
                System.out.println("User with id " + id + " not found.");
            }
            System.out.println("User with id " + id + " removed");
        } catch (Exception e) {
            System.err.println("Error occurred while removing user by id: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (SessionFactory sessionFactory = Util.provideSessionFactory()) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            users = session.createQuery("FROM User", User.class).list();
            transaction.commit();
            System.out.println("All users retrieved");
        } catch (Exception e) {
            System.err.println("Error occurred while retrieving all users: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (SessionFactory sessionFactory = Util.provideSessionFactory()) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
            System.out.println("Users table cleaned");
        } catch (Exception e) {
            System.err.println("Error occurred while cleaning users table: " + e.getMessage());
        }
    }

}
