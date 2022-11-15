package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService user = new UserServiceImpl();
        user.createUsersTable();
        user.createUsersTable();
        user.saveUser("Петя", "Петров", (byte) 24);
        user.saveUser("Вася", "Сидоров", (byte) 23);
        user.saveUser("Оля", "Семенов", (byte) 26);
        user.saveUser("Дима", "Орлов", (byte) 34);
        user.cleanUsersTable();
        user.removeUserById(3);
        user.removeUserById(4);
        user.removeUserById(4);
        user.removeUserById(4);
        System.out.println(user.getAllUsers());
        user.dropUsersTable();
        user.dropUsersTable();
        user.dropUsersTable();


    }
}

