package com.kuntsevich.testsys.controller.listener;

import com.kuntsevich.testsys.model.dao.pool.DatabaseConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DatabaseConnectionPoolListener implements ServletContextListener {
    private static final Logger log = Logger.getLogger(DatabaseConnectionPoolListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DatabaseConnectionPool connectionPool;
        connectionPool = DatabaseConnectionPool.getInstance();
        connectionPool.destroyPool();
    }
}
