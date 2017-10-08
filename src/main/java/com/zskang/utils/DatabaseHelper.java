package com.zskang.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DatabaseHelper {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static String URL = "";
    private static String DRIVER = "";
    private static String USERNAME = "";
    private static String PASSWORD = "";
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL_HOLDER = new ThreadLocal<Connection>();

    static {
        Properties properties = PropUtil.loadProps("db.propertise");
        URL = PropUtil.getString(properties, "jdbc.url");
        DRIVER = PropUtil.getString(properties, "jdbc.driver");
        USERNAME = PropUtil.getString(properties, "jdbc.username");
        PASSWORD = PropUtil.getString(properties, "jdbc.password");
        try {
            Class.forName(DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection conn = CONNECTION_THREAD_LOCAL_HOLDER.get();
        if (conn == null) {
            try {

                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                CONNECTION_THREAD_LOCAL_HOLDER.set(conn);
            }
        }
        return conn;
    }

    public static int excuteUpdate(String sql, Object... params) {
        int rows = 0;
        try {
            Connection connection = getConnection();
            rows = QUERY_RUNNER.update(connection, sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return rows;
    }

    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if (CollectionsUtil.isEmpty(fieldMap)) {
            logger.error("cant load fieldMap...");
            return false;
        }
        String sql = "INSERT INTO " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("( ");
        StringBuilder values = new StringBuilder(" ( ");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(",");
            values.append("?,");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += columns + " VALUES " + values;
        Object [] params=fieldMap.values().toArray();
        return excuteUpdate(sql,params)==1;
    }

    private static <T> String getTableName(Class<T> entityClass) {
        return entityClass.getSimpleName();
    }

    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList = null;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return entityList;
    }

    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity = null;
        try {
            Connection conn = DatabaseHelper.getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (Exception e) {
            logger.error("queryEntity failure ", e);
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return entity;
    }

    private static void closeConnection() {
        Connection conn = CONNECTION_THREAD_LOCAL_HOLDER.get();
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
