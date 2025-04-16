package org.Emberalive.accountDetailsView;

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

    public void startGUI() {
        this.view.setVisible(true);
    }

    public String getAccountDetails(String username) {
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
                      +  "Username: " + username
                + "\nRole: " + role);
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
        return null;
    }

    public boolean verifyPassword (String username, String password) {
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
                    return true;
                } else {
                    logger.info("Password not verified");
                    JOptionPane.showMessageDialog(view, "Invalid Password");
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to get the password for: {} Error: {}", username, e);
        }
        return false;
    }

    public boolean changePassword(String newPassword, String username) {
        Connection conn = Db_Access.getConnection();
        try {
            PreparedStatement stmnt = conn.prepareStatement("UPDATE users SET password=? WHERE username=?");
            stmnt.setString(1, newPassword);
            stmnt.setString(2, username);
            int rows = stmnt.executeUpdate();
            if (rows > 1) {
                logger.warn("Found more than one users with the username: {} Password not changed", username);
                conn.rollback();
                return false;
            } else {
                conn.commit();
                logger.info("Password changed");
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
        return false;
    }
}
