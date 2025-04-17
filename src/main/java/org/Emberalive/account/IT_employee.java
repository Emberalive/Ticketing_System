package org.Emberalive.account;

import org.Emberalive.db_access.Db_Access;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IT_employee {
    private static final Logger logger = LogManager.getLogger(IT_employee.class);

    private String username;
    private String password;
    private String role = "employee";

    public IT_employee(String username, String password) {
        this.username = username;
        this.password = Db_Access.hashPassword(password);
        boolean success = register(this.username, this.password, role);
        if (success) {
            logger.info("Employee registered successfully");
        } else {
            logger.info("Employee registration failed");
        }
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public boolean register(String username, String password, String role) {
        boolean success = false;
        Connection conn = Db_Access.getConnection();
        if (conn != null) {
            try {
                logger.info("Creating an account with username: {} and role: {}", username, role);
                //create the insert statement to insert the User to the database
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (username, password, role) VALUES (?, ?, ?)");
                //setting the username and password of the User for the query
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, role);
                //executing the query
                pstmt.executeUpdate();
                conn.commit();

                logger.info("The new User has been inserted into the database\n");
                success = true;
            } catch (SQLException e) {
                try {
                    conn.rollback(); // Rollback in case of error
                    logger.warn("Transaction rolled back.\n");
                } catch (SQLException rollbackEx) {
                    logger.error("Database err: {}\n", String.valueOf(rollbackEx));
                }
                logger.error("Database err: {}\n", String.valueOf(e));
            } finally {
                try {
                    conn.close();
                    logger.info("Connection closed\n");
                } catch (SQLException e) {
                    logger.warn("Database error when closing connection: {}\n", String.valueOf(e));   ;
                }
            }
        } else {
            logger.info("Connection failed\n");
        }
        return success;
    }
}

