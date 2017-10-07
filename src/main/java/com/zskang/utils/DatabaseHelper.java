package com.zskang.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

public class DatabaseHelper {
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static String URL = "";
    private static String USERNAME = "";
    private static String PASSWORD = "";
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL_HOLDER = new ThreadLocal<Connection>();

    static {
        Properties properties = PropUtil.loadProps("db.propertise");
        URL = PropUtil.getString(properties, "jdbc.url");
        USERNAME = PropUtil.getString(properties, "jdbc.username");
        PASSWORD = PropUtil.getString(properties, "jdbc.password");
    }

    public static Connection getConnection() {
        Connection conn = CONNECTION_THREAD_LOCAL_HOLDER.get();
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList = null;
        try {
            Connection conn = DatabaseHelper.getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return entityList;
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
