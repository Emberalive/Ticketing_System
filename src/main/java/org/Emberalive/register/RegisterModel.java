package org.Emberalive.register;

import org.Emberalive.db_access.Db_Access;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterModel {
    RegisterView view;
    private static final Logger logger = LogManager.getLogger(RegisterModel.class);

    public RegisterModel(RegisterView view) {
        this.view = view;
    }

    // registering the User into the database
    public boolean register(String username, String password, String role) {
        boolean success = false;
        Connection conn = Db_Access.getConnection();
        if (conn != null) {
            try {
                logger.info("\nCreating an account with username: {} and role: {}", username, role);
                //create the insert statement to insert the User to the database
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (username, password, role) VALUES (?, ?, ?)");
                //setting the username and password of the User for the query
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, role);
                //executing the query
                pstmt.executeUpdate();
                conn.commit();

                logger.info("\nThe new User has been inserted into the database");
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
                    logger.warn("Database error when closing connection: {}", String.valueOf(e));   ;
                }
            }
        } else {
            logger.info("\nConnection failed");
        }
        return success;
    }
}
