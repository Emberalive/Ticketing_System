package org.Emberalive.login;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.Emberalive.Employee.EmployeeController;
import org.Emberalive.Employee.EmployeeModel;
import org.Emberalive.Employee.EmployeeView;
import org.Emberalive.db_access.Db_Access;
import org.Emberalive.user.UserController;
import org.Emberalive.user.UserModel;
import org.Emberalive.user.UserView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    private static final Logger logger = LogManager.getLogger(LoginModel.class);
    Db_Access db = new Db_Access();

    public void login(String username, String password, LoginView loginView) {
        logger.info("Login attempt for User: {}", username);
        Connection conn = db.getConnection();
        String hashedPassword = "";
        String role = "";
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? ");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                hashedPassword = rs.getString(2);
                role = rs.getString(3);
            } else {
                logger.warn("No User found with username: {}", username);
                JOptionPane.showMessageDialog(loginView, "No User found with username: "+ username);

                return;
            }
        } catch (SQLException e){
            logger.error("\nDatabase err: {}", String.valueOf(e));
        } finally {
            try {
                conn.close();
            } catch (SQLException closeEx) {
                logger.error("\nDatabase err: {}", String.valueOf(closeEx));
            }
        }
        verifyUser(username, hashedPassword, password, loginView, role);
    }
    public void verifyUser(String username, String hashedPassword, String password, LoginView loginView, String role) {
        logger.info("Verifying User: {}", username);

        if (hashedPassword == null || hashedPassword.trim().isEmpty()) {
            logger.warn("\nThere is no account with username: {}", username);
            return;
        }

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        if (result != null && result.verified) {
            logger.info("Login successful");
            if (role.equals("employee")) {
                logger.info("Opening Employee: {}'s Account", username);
                EmployeeModel employeeModel = new EmployeeModel();
                EmployeeView employeeView = new EmployeeView(username);
                EmployeeController employeeController = new EmployeeController(employeeView ,employeeModel);

                loginView.setVisible(false);

                employeeController.startGUI();
            } else if (role.equals("user")) {
                logger.info("Opening User: {}'s Account", username);
                //initialize the UserController to start the GUI
                UserModel userModel = new UserModel();
                UserView userView = new UserView(username);
                UserController userController = new UserController(userModel, userView);

                loginView.setVisible(false);

                userController.startGUI();
            }
        } else {
            JOptionPane.showMessageDialog(loginView, "Invalid Password");
            logger.info("Login failed: incorrect password for User: {}", username);
        }
    }
}