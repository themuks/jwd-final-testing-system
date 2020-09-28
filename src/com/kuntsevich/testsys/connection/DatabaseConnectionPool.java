package com.kuntsevich.testsys.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class DatabaseConnectionPool {
    private static final String MYSQL_DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String AUTO_RECONNECT = "autoReconnect";
    private static final String CHARACTER_ENCODING = "characterEncoding";
    private static final String USE_UNICODE = "useUnicode";
    private static volatile DatabaseConnectionPool instance;
    private BlockingQueue<Connection> freeConnections;
    private Queue<Connection> givenAwayConnections;
    private final static int DEFAULT_POOL_SIZE = 32;

    private DatabaseConnectionPool() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        Properties properties = new Properties();
        //String driver = resourceBundle.getString("db.driver");
        String url = resourceBundle.getString("db.url");
        String user = resourceBundle.getString("db.user");
        String password = resourceBundle.getString("db.password");
        String autoReconnect = resourceBundle.getString("db.autoReconnect");
        String encoding = resourceBundle.getString("db.encoding");
        String useUnicode = resourceBundle.getString("db.useUnicode");
        properties.put(USER, user);
        properties.put(PASSWORD, password);
        properties.put(AUTO_RECONNECT, autoReconnect);
        properties.put(CHARACTER_ENCODING, encoding);
        properties.put(USE_UNICODE, useUnicode);
        freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(url, properties);
            } catch (SQLException throwables) {
                // TODO: 28.09.2020 Write logs
            }
            freeConnections.offer(connection);
        }
        givenAwayConnections = new ArrayDeque<>();
    }

    public static DatabaseConnectionPool getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnectionPool.class) {
                if (instance == null) {
                    instance = new DatabaseConnectionPool();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            // TODO: 28.09.2020 Write logs
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        givenAwayConnections.remove(connection);
        freeConnections.offer(connection);
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().close();
            } catch (SQLException e) {
                // TODO : Throw exception
            } catch (InterruptedException e) {
                // TODO : Throw exception
            }
        }
        deregisterDriver();
    }

    private void deregisterDriver() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                // TODO : Throw exception
            }
        });
    }
}
