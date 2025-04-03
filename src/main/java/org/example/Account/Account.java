package org.example.Account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DBAccess.db_access;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Account {
    private static final Logger logger = LogManager.getLogger(Account.class);
    // registering the user into the database
    public void register(String username, String password, String role) {
        db_access db = new db_access();
        Connection conn = db.getConnection();
        if (conn != null) {
            try {
                logger.info("\nCreating an account with username: {} and role: {}", username, role);
                //create the insert statement to insert the user to the database
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (username, password, role) VALUES (?, ?, ?)");
                //setting the username and password of the user for the query
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, role);
                //executing the query
                pstmt.executeUpdate();
                conn.commit();

                logger.info("\nThe new user has been inserted into the database");
            } catch (SQLException e) {
                try {
                    if (conn != null) {
                        conn.rollback(); // Rollback in case of error
                        logger.warn("\nTransaction rolled back.");
                    }
                } catch (SQLException rollbackEx) {
                    logger.error("\nDatabase err: {}", String.valueOf(rollbackEx));
                }
                logger.error("\nDatabase err: {}", String.valueOf(e));
            } finally {
                try {
                    conn.close();
                    logger.info("\nConnection closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            logger.info("\nConnection failed");
        }
    }
}
