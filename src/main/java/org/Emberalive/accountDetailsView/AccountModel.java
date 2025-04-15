package org.Emberalive.accountDetailsView;

import org.Emberalive.db_access.Db_Access;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            PreparedStatement stmnt = conn.prepareStatement("select * from account_details where username=?",
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

                accountDetails = String.format("Username: %s, Password: %s, Role: %s", username2, password, role);
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
}
