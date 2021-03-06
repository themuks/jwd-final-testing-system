package com.kuntsevich.ts.model.dao.util;

import com.kuntsevich.ts.model.dao.pool.DatabaseConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DaoUtil {
    private static final Logger log = Logger.getLogger(DaoUtil.class);
    private static final String EQUALS_STRING = " = ";
    private static final String AND_DELIMITER = " AND ";
    private static final char QUOTE = '\'';
    private static final String WHERE = " WHERE ";

    public static void releaseResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        releaseResources(connection, preparedStatement);
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                log.warn("Can't close result set", e);
            }
        }
    }

    public static void releaseResources(Connection connection, PreparedStatement preparedStatement) {
        DatabaseConnectionPool.getInstance().releaseConnection(connection);
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                log.warn("Can't close result set", e);
            }
        }
    }

    public String createQueryWithCriteria(String queryStart, Map<String, String> criteria) {
        List<String> conditions = new ArrayList<>();
        Set<String> keys = criteria.keySet();
        for (String key : keys) {
            StringBuilder sb = new StringBuilder();
            String condition = sb.append(key).append(EQUALS_STRING).append(QUOTE).append(criteria.get(key)).append(QUOTE).toString();
            conditions.add(condition);
        }
        StringJoiner query = new StringJoiner(AND_DELIMITER);
        for (String condition : conditions) {
            query.add(condition);
        }
        if (query.toString().isEmpty()) {
            return queryStart;
        }
        return queryStart + WHERE + query.toString();
    }
}
