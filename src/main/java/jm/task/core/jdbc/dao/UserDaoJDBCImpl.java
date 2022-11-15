package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createUsersTable = "CREATE TABLE  User (id MEDIUMINT NOT NULL AUTO_INCREMENT," +
                " name CHAR(30) NOT NULL ," +
                "lastName CHAR(30) NOT NULL ," +
                "age INT(10),PRIMARY KEY (id))";
        try (Connection connection = Util.connection();
             PreparedStatement statement = connection.prepareStatement(createUsersTable)) {
            statement.executeUpdate();
            System.out.println("Таблица успешно создана");
        } catch (SQLException e) {
            System.out.println("Таблица  уже  существует");
        }
    }

    public void dropUsersTable() {
        String dropUsersTable = "DROP table User";
        try (Connection connection = Util.connection();
             PreparedStatement statement = connection.prepareStatement(dropUsersTable)) {
            statement.executeUpdate();
            System.out.println("Таблица успешно удалена");
        } catch (SQLException e) {
            System.out.println("Таблица не существует");
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUser = "INSERT INTO User(name ,lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.connection();
             PreparedStatement statement = connection.prepareStatement(saveUser)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавление User");
        }

    }

    public void removeUserById(long id) {
        String removeUserById = "DELETE FROM User WHERE id = ?";
        try (Connection connection = Util.connection();
             PreparedStatement statement = connection.prepareStatement(removeUserById)) {
            statement.setLong(1, id);
            statement.execute();
            System.out.println("User с id = " + id + " успешно удален");
        } catch (SQLException e) {
            System.out.println("Ошибка  при удаление User c id = " + id);
        }

    }

    public List<User> getAllUsers() {
        List<User> listUser = new ArrayList<>();
        Statement statement;
        String getAllUsers = "SELECT * FROM User";
        try {
            Connection connection = Util.connection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getAllUsers);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                listUser.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Таблица отсутствует");
        }

        return listUser;
    }

    public void cleanUsersTable() {
        String cleanUsersTable = "TRUNCATE TABLE `User`;";
        try (Connection connection = Util.connection();
             Statement statement = connection.createStatement()) {
            statement.execute(cleanUsersTable);
            System.out.println("Таблица  полностью очищена");
        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицу");
        }

    }
}
