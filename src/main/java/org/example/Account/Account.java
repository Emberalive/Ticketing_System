package org.example.Account;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DBAccess.db_access;
import org.example.Main;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
    private static final Logger logger = LogManager.getLogger(Account.class);
    db_access db = new db_access();
    Main main = new Main();

    // registering the user into the database
    public boolean register(String username, String password, String role) {
        boolean success = false;
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
                success = true;
            } catch (SQLException e) {
                try {
                    conn.rollback(); // Rollback in case of error
                    logger.warn("\nTransaction rolled back.");
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
        return success;
    }

    //allows a user to delete their account
    public void deleteAccount(String username) {
        Connection conn = db.getConnection();
        try {
            logger.info("\nDeleting an account with username: {}", username);
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE username = ?");
            stmt.setString(1, username);

            stmt.executeUpdate();
            conn.commit();

            logger.info("\nThe account has been deleted from the database");
        } catch (SQLException e){
            logger.error("\nDatabase err: {}", String.valueOf(e));
            try {
                conn.rollback(); // Rollback in case of error
                logger.warn("\nTransaction rolled back.");
            } catch (SQLException rollbackEx) {
                logger.error("\nDatabase err: {}", String.valueOf(rollbackEx));
            } finally {
                try {
                    conn.close();
                } catch (SQLException closeEx) {
                    logger.error("\nDatabase err: {}", String.valueOf(closeEx));
                }
            }
        }
    }
}
