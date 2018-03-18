package com.zrbmyself.quickly.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/18 0018
 * Time: 13:07
 * Description:
 */
public class DBUtil {


    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/demo";
    private static final String username = "root";
    private static final String password = "root";

    private static ThreadLocal<Connection> connContainer = new ThreadLocal<>();

    public static Connection getConnection() {
        Connection conn  = connContainer.get();
        try {
            if(conn == null){
                Class.forName(driver);
                conn = DriverManager.getConnection(url, username, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            connContainer.set(conn);
        }
        return conn;
    }

    public static void closeConnection() {
        Connection conn  = connContainer.get();
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connContainer.remove();
        }
    }


}
