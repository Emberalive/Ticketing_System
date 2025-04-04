package org.example.LoginView;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DBAccess.db_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    private static final Logger logger = LogManager.getLogger(LoginView.class);
    db_access db = new db_access();

    public void login(String username, String password) {
        Connection conn = db.getConnection();
        String hashedPassword = "";
        BCrypt.Result result = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? ");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                hashedPassword = rs.getString(2);
            }
        } catch (SQLException e){
            logger.error("\nDatabase err: {}", String.valueOf(e));
            System.out.println("\nThere is no account with username: " + username);
        } finally {
            try {
                conn.close();
            } catch (SQLException closeEx) {
                logger.error("\nDatabase err: {}", String.valueOf(closeEx));
            }
        }
        if (hashedPassword.trim().isEmpty()) {
            System.out.println("\nThere is no account with username: " + username);
        } else {
            result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        }
        if (result.verified) {
            logger.info("Login successful");

        }
    }
}
