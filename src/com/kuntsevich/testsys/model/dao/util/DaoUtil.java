package com.kuntsevich.testsys.model.dao.util;

import java.util.*;

public class DaoUtil {
    private static final String EQUALS_STRING = " = ";
    private static final String AND_DELIMITER = " AND ";
    private static final char QUOTE = '\'';

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
        return queryStart + query.toString();
    }
}
