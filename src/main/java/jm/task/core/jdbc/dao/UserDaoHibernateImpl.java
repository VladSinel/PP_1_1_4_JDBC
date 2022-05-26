package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users" +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(25), " +
                    "lastName VARCHAR(25), age TINYINT(200))").executeUpdate();
            transaction.commit();
            System.out.println("Table was created");
        } catch (Exception h) {
            System.out.println("Creating table problems...");
            System.out.println(h.getMessage());
            h.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
            System.out.println("Table was deleted");
        } catch (Exception h) {
            System.out.println("Deleting table problems...");
            System.out.println(h.getMessage());
            h.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("Saving users complete");
        } catch (Exception h) {
            System.out.println("Saving users problems...");
            System.out.println(h.getMessage());
            h.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.println("Removing user complete");
        } catch (Exception h) {
            System.out.println("Removing user problems...");
            System.out.println(h.getMessage());
            h.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<User> usersList = new ArrayList<>();
        try (session) {
            usersList =  session.createQuery("from User").list();
            transaction.commit();
            System.out.println("Getting users complete");
        } catch (Exception h) {
            System.out.println("Getting users list problems...");
            System.out.println(h.getMessage());
            h.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            transaction.commit();
            System.out.println("Cleaning users is successful");
        } catch (Exception h) {
            System.out.println("Cleaning users problems...");
            System.out.println(h.getMessage());
            h.printStackTrace();
        }
    }
}