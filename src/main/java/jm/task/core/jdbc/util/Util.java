package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String LOGIN = "jmuser@loclhost";
    private static final String PASSWORD = "jmuser";
    private static final String DATABASE_NAME = "jm_core_task";
    private static final String DATABASE_INFO = "serverTimezone=Europe/Moscow&useSSL=false";


    private Connection connection;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL + DATABASE_NAME + "?" + DATABASE_INFO, LOGIN, PASSWORD);
    }

}