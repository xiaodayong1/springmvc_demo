package com.lagou.edu.utils;

import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 应癫
 */
public class ConnectionUtils {

    private ConnectionUtils(){
        System.out.println("无参构造");
    }

    private static ConnectionUtils connectionUtils=new ConnectionUtils();

    public static ConnectionUtils getInstance() {
        return connectionUtils;
    }

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection> ();

    public Connection getCurrentThreadConn() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection == null){
            connection= DruidUtils.getInstance().getConnection();
            threadLocal.set(connection);
        }
        return connection;
    }
}
