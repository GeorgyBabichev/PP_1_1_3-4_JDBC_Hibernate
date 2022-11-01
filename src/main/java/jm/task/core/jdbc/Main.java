package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserDao user = new UserDaoJDBCImpl();
        user.createUsersTable();
        user.saveUser("Петя", "Петров", (byte) 24);
        user.saveUser("Вася", "Сидоров", (byte) 23);
        user.saveUser("Оля", "Семенов", (byte) 26);
        user.saveUser("Дима", "Орлов", (byte) 34);
        System.out.println(user.getAllUsers());
        user.cleanUsersTable();
        user.dropUsersTable();
    }
}

