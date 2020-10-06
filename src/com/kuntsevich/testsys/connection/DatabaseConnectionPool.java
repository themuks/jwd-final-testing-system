package com.kuntsevich.testsys.connection;

import com.kuntsevich.testsys.exception.DatabasePoolException;
import com.mysql.jdbc.Driver;
import org.apache.log4j.Logger;

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
    private static final Logger log = Logger.getLogger(DatabaseConnectionPool.class);
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String AUTO_RECONNECT = "autoReconnect";
    private static final String CHARACTER_ENCODING = "characterEncoding";
    private static final String USE_UNICODE = "useUnicode";
    private final static int DEFAULT_POOL_SIZE = 32;
    private static volatile DatabaseConnectionPool instance;
    private BlockingQueue<Connection> freeConnections;
    private Queue<Connection> givenAwayConnections;

    private DatabaseConnectionPool() throws SQLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        Properties properties = new Properties();
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
        DriverManager.registerDriver(new Driver());
        freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            Connection connection;
            connection = DriverManager.getConnection(url, properties);
            freeConnections.offer(connection);
        }
        givenAwayConnections = new ArrayDeque<>();
    }

    public static DatabaseConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            synchronized (DatabaseConnectionPool.class) {
                if (instance == null) {
                    instance = new DatabaseConnectionPool();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws DatabasePoolException {
        Connection connection;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            throw new DatabasePoolException("Can't get connection form connection queue", e);
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
                log.warn("Can't access database to close connection", e);
            } catch (InterruptedException e) {
                log.warn("Can't take connection from queue", e);
            }
        }
        deregisterDriver();
    }

    private void deregisterDriver() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                log.warn("Can't access database to deregister driver", e);
            }
        });
    }
}
