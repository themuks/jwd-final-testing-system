package com.kuntsevich.ts.model.dao.pool;

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
    private static final int DEFAULT_POOL_SIZE = 32;
    private static final String DATABASE_BUNDLE_NAME = "database";
    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_AUTO_RECONNECT = "db.autoReconnect";
    private static final String DB_ENCODING = "db.encoding";
    private static final String DB_USE_UNICODE = "db.useUnicode";
    private static final DatabaseConnectionPool instance = new DatabaseConnectionPool();
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> givenAwayConnections;

    private DatabaseConnectionPool() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DATABASE_BUNDLE_NAME);
        Properties properties = new Properties();
        String url = resourceBundle.getString(DB_URL);
        String user = resourceBundle.getString(DB_USER);
        String password = resourceBundle.getString(DB_PASSWORD);
        String autoReconnect = resourceBundle.getString(DB_AUTO_RECONNECT);
        String encoding = resourceBundle.getString(DB_ENCODING);
        String useUnicode = resourceBundle.getString(DB_USE_UNICODE);
        properties.put(USER, user);
        properties.put(PASSWORD, password);
        properties.put(AUTO_RECONNECT, autoReconnect);
        properties.put(CHARACTER_ENCODING, encoding);
        properties.put(USE_UNICODE, useUnicode);
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            log.warn("Error while registering driver", e);
        }
        freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            Connection connection;
            try {
                connection = DriverManager.getConnection(url, properties);
            } catch (SQLException e) {
                log.fatal("Error while getting connection from driver manager", e);
                throw new RuntimeException("Error while getting connection from driver manager", e);
            }
            ProxyConnection proxyConnection = new ProxyConnection(connection);
            freeConnections.offer(proxyConnection);
        }
        givenAwayConnections = new ArrayDeque<>();
    }

    public static DatabaseConnectionPool getInstance() {
        return instance;
    }

    public Connection getConnection() {
        ProxyConnection connection;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            log.fatal("Can't get connection form connection queue", e);
            throw new RuntimeException("Error while getting connection from queue", e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection) {
            givenAwayConnections.remove(connection);
            freeConnections.offer((ProxyConnection) connection);
        } else {
            log.warn("Invalid connection object provided");
        }
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().close();
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
