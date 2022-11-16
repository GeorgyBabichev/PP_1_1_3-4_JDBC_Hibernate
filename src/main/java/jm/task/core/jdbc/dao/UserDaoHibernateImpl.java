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
        Transaction transaction = null;
        String createUsersTable = "CREATE TABLE IF NOT EXISTS  Users (id MEDIUMINT NOT NULL AUTO_INCREMENT," +
                " name CHAR(30) NOT NULL ," +
                "lastName CHAR(30) NOT NULL ," +
                "age INT(10),PRIMARY KEY (id))";
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(createUsersTable).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            System.out.println("Таблица  уже  существует");
            if (transaction != null){
                transaction.rollback();
            }
        }
    }

    @Override
    @Transactional
    public void dropUsersTable() {
        Transaction transaction = null;
        String dropUsersTable = "DROP TABLE IF EXISTS Users";
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(dropUsersTable).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            System.out.println("Таблица не существует");
            if (transaction != null){
                transaction.rollback();
            }
        }
    }

    @Override
    @Transactional
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            User user = new User(name, lastName, age);
            transaction = session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User успешно добавлен");
        } catch (Exception e) {
            System.out.println("Ошибка при сохранение User");
            if (transaction != null){
                transaction.rollback();
            }
        }
    }


    @Override
    @Transactional
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
            System.out.println("User с id " + id + " был успешно удален");
        } catch (Exception e) {
            System.out.println("Ошибка при удаение User с id = " + id);if (transaction != null){
                transaction.rollback();
            }
        }
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> userList = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            userList = session.createQuery(criteriaQuery).getResultList();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Не удалось вывести таблицу");
            if (transaction != null){
                transaction.rollback();
            }
        }
        return userList;
    }

    @Override
    @Transactional
    public void cleanUsersTable() {
        Transaction transaction = null;
        String cleanUsersTable = "TRUNCATE TABLE Users;";
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(cleanUsersTable).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица очищена");
        } catch (Exception e) {
            System.out.println("Таблица не существует");
            if (transaction != null){
                transaction.rollback();
            }
        }

    }
}
