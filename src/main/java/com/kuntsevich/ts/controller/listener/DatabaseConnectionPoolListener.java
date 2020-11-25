package com.kuntsevich.ts.controller.listener;

import com.kuntsevich.ts.model.dao.pool.DatabaseConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DatabaseConnectionPoolListener implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DatabaseConnectionPool.getInstance().destroyPool();
    }
}