package org.example.DBAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class db_access {
    private static final Logger logger = LogManager.getLogger(db_access.class);
    private static final String URL = "jdbc:postgresql://86.19.219.159:5432/itticketing";
    private static final String USER = "samuel";
    private static final String PASSWORD = "QwErTy1243!";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (conn != null && conn.isValid(2)) {
                logger.info("Database connection established!");
                conn.setAutoCommit(false);
            } else {
                logger.info("Database connection not established!");
            }
        } catch (SQLException e) {
            logger.error("Database error: {}", String.valueOf(e));
        }
        return conn;
    }
}
