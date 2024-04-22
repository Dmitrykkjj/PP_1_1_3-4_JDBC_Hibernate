package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection;

    static {
        try {
            connection = Util.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS user (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(50) NOT NULL," +
                "lastName VARCHAR(50) NOT NULL," +
                "age TINYINT NOT NULL)";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Таблица пользователей успешно создана");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        String dropTableSQL = "DROP TABLE IF EXISTS user";

        try (Statement statement = connection.createStatement()) {
            statement.execute(dropTableSQL);
            System.out.println("Таблица пользователей успешно удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertUserSQL = "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Добавлено " + rowsAffected + " записей");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String removeUserSQL = "DELETE FROM user WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(removeUserSQL)) {
            preparedStatement.setLong(1, id);

            // Выполняем запрос на удаление
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Пользователь с ID " + id + " успешно удален");
            } else {
                System.out.println("Пользователь с ID " + id + " не найден");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        String getAllUsersSQL = "SELECT * FROM user";

        try (PreparedStatement preparedStatement = connection.prepareStatement(getAllUsersSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                User user = new User(id, name, lastName, age);
                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public void cleanUsersTable() {
        String cleanUsersSQL = "TRUNCATE TABLE user";

        try (PreparedStatement preparedStatement = connection.prepareStatement(cleanUsersSQL)) {
            preparedStatement.executeUpdate();
            System.out.println("Таблица пользователей очищена");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
