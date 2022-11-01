package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DB_URL = "jdbc:mysql://localhost:3306/User";
    private static final String DB_Driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "root";
    private static final String password = "root";

    public static Connection connection(){
        Connection connection =null;
        try {
            Class.forName(DB_Driver);
              connection = DriverManager.getConnection(DB_URL,userName,password);
            System.out.println("Соединение с MySQL выполнено.");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC драйвер для MySQL не найден!");
        } catch (SQLException e) {
            System.out.println("Ошибка SQL !");
        }
        return connection;
    }

    public static void closeConnection(Connection x){
        try {
            if (x != null){
            x.close();
            System.out.println("Соединение с MySQL завершено.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
