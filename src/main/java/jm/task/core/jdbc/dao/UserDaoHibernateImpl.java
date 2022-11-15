package jm.task.core.jdbc.dao;

import jakarta.transaction.Transactional;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    public UserDaoHibernateImpl() {

    }


    @Override
    @Transactional
    public void createUsersTable() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS  Users (id MEDIUMINT NOT NULL AUTO_INCREMENT," +
                " name CHAR(30) NOT NULL ," +
                "lastName CHAR(30) NOT NULL ," +
                "age INT(10),PRIMARY KEY (id))";
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(createUsersTable).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            System.out.println("Таблица  уже  существует");

        }
    }

    @Override
    @Transactional
    public void dropUsersTable() {
        String dropUsersTable = "DROP TABLE IF EXISTS Users";
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(dropUsersTable).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            System.out.println("Таблица не существует");

        }
    }

    @Override
    @Transactional
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User успешно добавлен");
        } catch (Exception e) {
            System.out.println("Ошибка при сохранение User");
        }
    }


    @Override
    @Transactional
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
            System.out.println("User с id " + id + " был успешно удален");
        } catch (Exception e) {
            System.out.println("Ошибка при удаение User с id = " + id);
        }
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        List<User> userList = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            userList = session.createQuery(criteriaQuery).getResultList();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Не удалось вывести таблицу");

        }
        return userList;
    }

    @Override
    @Transactional
    public void cleanUsersTable() {
        String cleanUsersTable = "TRUNCATE TABLE Users;";
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(cleanUsersTable).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица очищена");
        } catch (Exception e) {
            System.out.println("Таблица не существует");

        }

    }
}
