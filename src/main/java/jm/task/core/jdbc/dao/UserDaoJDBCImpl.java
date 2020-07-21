package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


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
    private static final String CLEAR_TABLE_QUERY =
            "DELETE FROM users;";

    private static final String INSERT_USER_IN_TABLE =
            "INSERT INTO users (name,last_name, age) VALUES(?, ?, ?);";

    private static final String DELETE_USER_FROM_TABLE =
            "DELETE FROM users WHERE id = ?;";

    private static final String SELECT_ALL_USERS_FROM_TABLE =
            "SELECT * FROM users;";


    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.addBatch(DROP_TABLE_QUERY);
            statement.addBatch(CREATE_TABLE_QUERY);
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_TABLE_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatementInsert = connection.prepareStatement(INSERT_USER_IN_TABLE)) {
            preparedStatementInsert.setString(1, name);
            preparedStatementInsert.setString(2, lastName);
            preparedStatementInsert.setByte(3, age);
            preparedStatementInsert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatementDelete = connection.prepareStatement(DELETE_USER_FROM_TABLE)) {
            preparedStatementDelete.setLong(1, id);
            preparedStatementDelete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS_FROM_TABLE)) {
            while (resultSet.next()) {
                users.add(new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4)));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    @Override
    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CLEAR_TABLE_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




