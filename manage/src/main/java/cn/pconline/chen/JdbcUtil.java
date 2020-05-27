package cn.pconline.chen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {
    private static Connection conn = null;
    // jdbc:mysql://192.168.75.99:3306/download?useUnicode=true&amp;characterEncoding=GBK&amp;zeroDateTimeBehavior=convertToNull
    private static final String URL = "jdbc:mysql://192.168.75.99:3306/download";
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String USER_NAME = "download_app";
    private static final String PASSWORD = "download_app";

    public static Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
