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
                "name VARCHAR(255) NOT NULL," +
                "lastName VARCHAR(255) NOT NULL," +
                "age TINYINT NOT NULL)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
            preparedStatement.execute();
            System.out.println("Таблица пользователей успешно создана");
        } catch (SQLException e) {
            System.err.println("Таблица пользователей уже существует" + e.getMessage());
        }
    }

    public void dropUsersTable() {

        String dropTableSQL = "DROP TABLE IF EXISTS user";

        try (PreparedStatement preparedStatement = connection.prepareStatement(dropTableSQL)) {
            preparedStatement.execute();
            System.out.println("Таблица пользователей успешно удалена");
        } catch (SQLException e) {
            System.err.println("Таблица пользователей уже отсутствует" + e.getMessage());
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
            System.err.println("Ошибка при добавлении пользователя: " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String removeUserSQL = "DELETE FROM user WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(removeUserSQL)) {
            preparedStatement.setLong(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Пользователь с ID " + id + " успешно удален");
            } else {
                System.out.println("Пользователь с ID " + id + " не найден");
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
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

                User user = User.builder()
                        .id(id)
                        .name(name)
                        .lastName(lastName)
                        .age(age)
                        .build();
                userList.add(user);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при получении списка пользователей: " + e.getMessage());
        }

        return userList;
    }

    public void cleanUsersTable() {
        String cleanUsersSQL = "TRUNCATE TABLE user";

        try (PreparedStatement preparedStatement = connection.prepareStatement(cleanUsersSQL)) {
            preparedStatement.executeUpdate();
            System.out.println("Таблица пользователей очищена");

        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы пользователей: " + e.getMessage());
        }
    }
}
