package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users" +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(25), lastName VARCHAR(25), age TINYINT(200))");

        } catch (SQLException e) {
            System.out.println("Creating table problems...");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Table was created");
    }

    @Override
    public void dropUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");

        } catch (SQLException e) {
            System.out.println("Deleting table problems...");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Table was deleted");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, lastName, age)" +
                "VALUES (?, ?, ?)")) {
            statement.setString(1,name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Saving users problems...");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Saving users complete");
    }

    @Override
    public void removeUserById(long id) {
        try(PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Removing user problems...");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Removing user complete");
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
                ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong(1));
                user.setName(result.getString(2));
                user.setLastName(result.getString(3));
                user.setAge(result.getByte(4));
                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Get users list problems...");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Getting users complete");
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");

        } catch (SQLException e) {
            System.out.println("Cleaning users problems...");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Cleaning users is successful");
    }
}
