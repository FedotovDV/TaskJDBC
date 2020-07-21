package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final String DROP_TABLE_QUERY =
            "DROP TABLE IF EXISTS users;";
    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE  users ("
                    + "`id` bigint NOT NULL AUTO_INCREMENT,"
                    + "`name` varchar(45) DEFAULT NULL,"
                    + "`last_name` varchar(45) DEFAULT NULL,"
                    + "`age` tinyint DEFAULT NULL,"
                    + " PRIMARY KEY(`id`)"
                    + ") AUTO_INCREMENT = 1 "
                    + "DEFAULT CHARSET = utf8;";


    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        goTransaction(CREATE_TABLE_QUERY);
    }

    @Override
    public void dropUsersTable() {
        goTransaction(DROP_TABLE_QUERY);
    }

    private void goTransaction(String createTableQuery) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery(createTableQuery)
                    .executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.createQuery(
                    "delete User u where u.id = :oldId")
                    .setParameter("oldId", id)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            users = (List<User>)session.createQuery("from User").getResultList();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return users;
    }


    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User ")
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
