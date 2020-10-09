package com.kuntsevich.testsys.listener;

import com.kuntsevich.testsys.pool.DatabaseConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

public class DatabaseConnectionPoolListener implements ServletContextListener {
    private static final Logger log = Logger.getLogger(DatabaseConnectionPoolListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DatabaseConnectionPool connectionPool;
        try {
            connectionPool = DatabaseConnectionPool.getInstance();
            connectionPool.destroyPool();
        } catch (SQLException e) {
            log.error("Can't get instance of database connection pool to destroy connection pool", e);
        }
    }
}
