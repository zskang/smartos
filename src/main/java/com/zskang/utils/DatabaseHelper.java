package com.zskang.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

public class DatabaseHelper {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static String URL = "";
    private static String USERNAME = "";
    private static String PASSWORD = "";
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL_HOLDER = new ThreadLocal<Connection>();

    public static Connection getConnection() {
        Connection conn = CONNECTION_THREAD_LOCAL_HOLDER.get();
        if (conn == null) {
            try {
                Properties properties = PropUtil.loadProps("db.propertise");
                URL = PropUtil.getString(properties, "jdbc.url");
                USERNAME = PropUtil.getString(properties, "jdbc.username");
                PASSWORD = PropUtil.getString(properties, "jdbc.password");
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                CONNECTION_THREAD_LOCAL_HOLDER.set(conn);
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

    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity = null;
        try {
            Connection conn = DatabaseHelper.getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (Exception e) {
            logger.error("queryEntity failure ", e);
            e.printStackTrace();
        }finally {
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
