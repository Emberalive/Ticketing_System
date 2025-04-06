package org.example.login;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.db_access.Db_Access;
import org.example.user.UserController;
import org.example.user.UserModel;
import org.example.user.UserView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    private static final Logger logger = LogManager.getLogger(LoginModel.class);
    Db_Access db = new Db_Access();

    public void login(String username, String password, LoginView loginView) {
        logger.info("Login attempt for User: {}", username); // <--- NEW
        Connection conn = db.getConnection();
        String hashedPassword = "";
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? ");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                hashedPassword = rs.getString(2);
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
        verifyUser(username, hashedPassword, password, loginView);
    }
    public void verifyUser(String username, String hashedPassword, String password, LoginView loginView) {
        logger.info("Verifying User: {}", username); // <--- NEW

        if (hashedPassword == null || hashedPassword.trim().isEmpty()) {
            logger.warn("\nThere is no account with username: {}", username);
            return;
        }

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        if (result != null && result.verified) {
            logger.info("Login successful");
            //initialize the UserController to start the GUI
            UserModel userModel = new UserModel();
            UserView userView = new UserView(username);
            UserController userController = new UserController(userModel, userView);

            loginView.setVisible(false);

            userController.startGUI();
        } else {
            logger.info("Login failed: incorrect password for User: {}", username);
        }
    }
}



// Implement search filter logic
//        searchField.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String searchText = searchField.getText().toLowerCase();
//                String[] items = {"Dashboard", "Profile", "Settings", "Logout", "Help", "Reports", "Analytics"};
//                DefaultListModel<String> filteredListModel = new DefaultListModel<>();
//                for (String item : items) {
//                    if (item.toLowerCase().contains(searchText)) {
//                        filteredListModel.addElement(item);
//                    }
//                }
//                listView.setModel(filteredListModel);
//            }
//        });