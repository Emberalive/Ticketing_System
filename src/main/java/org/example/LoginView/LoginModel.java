package org.example.LoginView;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DBAccess.db_access;
import org.example.UserView.UserController;
import org.example.UserView.UserModel;
import org.example.UserView.UserView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    private static final Logger logger = LogManager.getLogger(LoginModel.class);
    db_access db = new db_access();

    public void login(String username, String password) {
        logger.info("Login attempt for user: {}", username); // <--- NEW
        Connection conn = db.getConnection();
        String hashedPassword = "";
        BCrypt.Result result = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? ");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                hashedPassword = rs.getString("password");
            } else {
                logger.warn("No user found with username: {}", username);
                return; // Exit early — no need to continue
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
        verifyUser(username, hashedPassword, password);
    }
    public void verifyUser(String username, String hashedPassword, String password) {
        logger.info("Verifying user: {}", username); // <--- NEW
        BCrypt.Result result = null;

        if (hashedPassword.trim().isEmpty()) {
            System.out.println("\nThere is no account with username: " + username);
        } else {
            result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
            System.out.println("\nUser found with username: " + username);
        }
        if (result != null && result.verified) {
            System.out.println("\nUser logged in!");
            logger.info("Login successful");
            //initialize the UserController to start the GUI
            UserModel userModel = new UserModel();
            UserView userView = new UserView();
            UserController userController = new UserController(userModel, userView);

            userController.startGUI();
        } else {
            logger.info("Login failed");
        }
    }
}
