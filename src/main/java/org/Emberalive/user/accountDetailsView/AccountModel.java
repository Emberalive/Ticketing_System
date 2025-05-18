package org.Emberalive.user.accountDetailsView;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.Emberalive.db_access.Db_Access;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountModel {
    private static final Logger logger = LogManager.getLogger(AccountModel.class);
    AccountView view;

    public AccountModel(AccountView view) {
        this.view = view;
    }

    public AccountView startGUI() {
        this.view.setVisible(true);
        return this.view;
    }

    public String getAccountDetails(String username) {
        logger.info("---- Start getAccountDetails() ----");
        Connection conn = Db_Access.getConnection();
        String accountDetails;

        try {
            PreparedStatement stmnt = conn.prepareStatement("select * from users where username=?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            stmnt.setString(1, username);
            ResultSet rs = stmnt.executeQuery();

            rs.last();
            int rows = rs.getRow();
            rs.beforeFirst();
            if (rows > 1) {
                logger.warn("Found more than one users with the username: {}", username);
            } else {
                rs.next();
                String username2 = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");

                accountDetails = ("Account Details:\n" +
                        "--------------------------------------------------------------------------------------------------------------------------\n"
                        + "Username: " + username2
                        + "\nRole: " + role);
                logger.info("---- End getAccountDetails() ----\n");
                return accountDetails;
            }

        } catch (SQLException e) {
            logger.error("Failed to get the account Details for: {} Error: {}", username, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException close) {
                logger.error("Failed to close the database connection", close);
            }
        }

        logger.info("---- End getAccountDetails() ----\n");
        return null;
    }

    public boolean verifyPassword(String username, String password) {
        logger.info("---- Start verifyPassword() ----");
        Connection conn = Db_Access.getConnection();
        try {
            logger.info("getting password for: {}", username);
            PreparedStatement stmnt = conn.prepareStatement("SELECT * FROM users WHERE username=?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            stmnt.setString(1, username);
            ResultSet rs = stmnt.executeQuery();
            rs.last();
            int rows = rs.getRow();
            rs.beforeFirst();
            if (rows == 1) {
                rs.next();
                String hashedPassword = rs.getString("password");
                if (BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified) {
                    logger.info("Password verified");
                    logger.info("---- End verifyPassword() ----\n");
                    return true;
                } else {
                    logger.info("Password not verified");
                    JOptionPane.showMessageDialog(view, "Invalid Password");
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to get the password for: {} Error: {}", username, e);
        }

        logger.info("---- End verifyPassword() ----\n");
        return false;
    }

    public boolean changePassword(String newPassword, String username) {
        logger.info("---- Start changePassword() ----");
        Connection conn = Db_Access.getConnection();
        try {
            conn.setAutoCommit(false);

            String hashedPassword = Db_Access.hashPassword(newPassword);

            PreparedStatement stmnt = conn.prepareStatement("UPDATE users SET password=? WHERE username=?");
            stmnt.setString(1, hashedPassword);
            stmnt.setString(2, username);
            int rows = stmnt.executeUpdate();
            if (rows > 1 || rows == 0) {
                logger.warn("Issues with changing the password for user: {}. Password not changed", username);
                conn.rollback();
                logger.info("---- End changePassword() ----\n");
                return false;
            } else {
                conn.commit();
                logger.info("Password changed");
                logger.info("---- End changePassword() ----\n");
                return true;
            }
        } catch (SQLException e) {
            logger.error("Failed to change the password for user", e);
        } finally {
            try {
                conn.close();
            } catch (SQLException close) {
                logger.error("Failed to close the database connection", close);
            }
        }

        logger.info("---- End changePassword() ----\n");
        return false;
    }

    public boolean deleteAccount(String username) {
        logger.info("---- Start deleteAccount() ----");
        Connection conn = Db_Access.getConnection();
        boolean deleteTickets = deleteTickets(username);

        try {
            conn.setAutoCommit(false);
            logger.info("Deleting an account with username: {}", username);
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE username = ?");
            stmt.setString(1, username);

            int rows = stmt.executeUpdate();
            if (rows == 1 && deleteTickets) {
                conn.commit();
                logger.info("The account has been deleted from the database");
                logger.info("---- End deleteAccount() ----\n");
                return true;
            } else {
                conn.rollback();
                logger.info("The account could not be deleted");
            }
        } catch (SQLException e) {
            logger.error("Database err: {}", String.valueOf(e));
            try {
                conn.rollback(); // Rollback in case of error
                logger.warn("Transaction rolled back.");
            } catch (SQLException rollbackEx) {
                logger.error("Error rolling back the database: {}", rollbackEx.getMessage());
            } finally {
                try {
                    conn.close();
                } catch (SQLException closeEx) {
                    logger.error("Error closing the connection: {}", closeEx.getMessage());
                }
            }
        }

        logger.info("---- End deleteAccount() ----\n");
        return false;
    }

    public boolean deleteTickets(String username) {
        logger.info("---- Start deleteTickets() ----");
        Connection conn = Db_Access.getConnection();
        try {
            conn.setAutoCommit(false);
            PreparedStatement stmnt = conn.prepareStatement("DELETE FROM ticket WHERE username = ?");
            stmnt.setString(1, username);
            stmnt.executeUpdate();
            conn.commit();
            logger.info("Tickets deleted for username: {}", username);
            logger.info("---- End deleteTickets() ----\n");
            return true;
        } catch (SQLException e) {
            logger.error("Failed to get the tickets for: {} Error: {}", username, e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException close) {
                logger.error("Failed to close the database connection Error: {}", close.getMessage());
            }
        }

        logger.info("---- End deleteTickets() ----\n");
        return false;
    }
}
