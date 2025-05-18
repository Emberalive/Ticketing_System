package org.Emberalive.user.accountDetailsView;

import org.Emberalive.login.LoginController;
import org.Emberalive.login.LoginModel;
import org.Emberalive.login.LoginView;
import org.Emberalive.user.UserView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class AccountView extends JFrame {
    private static final Logger logger = LogManager.getLogger(AccountView.class);

    // MVC Components
    AccountModel model = new AccountModel(this);

    // UI Components
    private final JLabel accountLabel;
    private final JTextArea accountDetailsArea;
    private final JButton getAccountDetails;
    private final JLabel changePassLabel;
    private final JLabel newPasswordLabel;
    private final JPasswordField newPassword;
    private final JLabel confirmPasswordLabel;
    private final JPasswordField confirmPassword;
    private final JLabel verifyPassword;
    private final JPasswordField verifyPassField;
    private final JButton changePasswordButton;
    private final JButton deleteAccountButton;
    private final JButton verifyPasswordButton;

    public AccountView(String username, UserView userView) {
        // Set JFrame properties
        setTitle("Account Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        setLayout(null);

        // Initialize components
        accountLabel = new JLabel("Account Details: " + username + "!");
        accountLabel.setBounds(50, 10, 250, 30);

        accountDetailsArea = new JTextArea();
        accountDetailsArea.setEditable(false);
        accountDetailsArea.setPreferredSize(new Dimension(500, 75));
        accountDetailsArea.setBounds(60, 50, 500, 75);

        getAccountDetails = new JButton("Account Details");
        getAccountDetails.setBounds(50, 410, 200, 30);

        changePassLabel = new JLabel("Change Password?");
        changePassLabel.setBounds(50, 135, 175, 30);
        changePassLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        newPasswordLabel = new JLabel("New Password");
        newPasswordLabel.setBounds(50, 290, 175, 30);

        newPassword = new JPasswordField();
        newPassword.setBounds(190, 290, 175, 30);
        newPassword.setEnabled(false);

        confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setBounds(50, 330, 175, 30);

        confirmPassword = new JPasswordField();
        confirmPassword.setBounds(190, 330, 175, 30);
        confirmPassword.setEnabled(false);

        verifyPassword = new JLabel("Verify Password");
        verifyPassword.setBounds(50, 175, 175, 30);

        verifyPassField = new JPasswordField();
        verifyPassField.setBounds(175, 175, 175, 30);

        changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(175, 370, 175, 30);
        changePasswordButton.setEnabled(false);

        deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.setBounds(260, 410, 175, 30);

        verifyPasswordButton = new JButton("Verify Password");
        verifyPasswordButton.setBounds(175, 250, 175, 30);

        // Action Listeners
        getAccountDetails.addActionListener(e -> {
            logger.info("---- Start getAccountDetails listener ----");
            logger.info("Getting Account Details for user: {}", username);
            String accountDetails = model.getAccountDetails(username);
            if (accountDetails != null) {
                accountDetailsArea.setText(accountDetails);
            } else {
                logger.error("Account details could not be retrieved from the Database");
            }
            logger.info("---- End getAccountDetails listener ----\n");
        });

        verifyPasswordButton.addActionListener(e -> {
            logger.info("---- Start verifyPassword listener ----");
            logger.info("Verifying Password to change password for: {}", username);
            String verifyPass = String.valueOf(verifyPassField.getPassword());
            if (model.verifyPassword(username, verifyPass)) {
                confirmPassword.setEnabled(true);
                newPassword.setEnabled(true);
                changePasswordButton.setEnabled(true);
                verifyPassField.setText("");
                verifyPassField.setEnabled(false);
            }
            logger.info("---- End verifyPassword listener ----\n");
        });

        changePasswordButton.addActionListener(e -> {
            logger.info("---- Start changePassword listener ----");
            logger.info("Changing Password for user: {}", username);
            String newPass = String.valueOf(newPassword.getPassword());
            String confirmPass = String.valueOf(confirmPassword.getPassword());
            if (!newPass.equals(confirmPass)) {
                logger.info("Passwords do not match when trying to change the password for user: {}", username);
                JOptionPane.showMessageDialog(AccountView.this, "Passwords do not match!");
            } else {
                boolean check = model.changePassword(newPass, username);
                if (check) {
                    JOptionPane.showMessageDialog(AccountView.this, "Password changed!");
                    newPassword.setText("");
                    confirmPassword.setText("");
                    confirmPassword.setEnabled(false);
                    newPassword.setEnabled(false);
                    changePasswordButton.setEnabled(false);
                    verifyPassField.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(AccountView.this, "Password was not changed!");
                }
            }
            logger.info("---- End changePassword listener ----\n");
        });

        deleteAccountButton.addActionListener(e -> {
            logger.info("---- Start deleteAccount listener ----");
            logger.info("Deleting an account with username: {}", username);
            boolean success = model.deleteAccount(username);
            if (success) {
                JOptionPane.showMessageDialog(AccountView.this, "Account has been deleted from the database");

                this.dispose();
                userView.dispose();

                LoginView loginView = new LoginView();
                LoginModel loginModel = new LoginModel();
                LoginController loginController = new LoginController(loginModel, loginView);
                loginController.startGUI();
            } else {
                JOptionPane.showMessageDialog(this, "Issues with deleting the account");
            }
            logger.info("---- End deleteAccount listener ----\n");
        });

        // Add components to the JFrame
        add(accountLabel);
        add(accountDetailsArea);
        add(getAccountDetails);
        add(changePassLabel);
        add(newPasswordLabel);
        add(newPassword);
        add(confirmPasswordLabel);
        add(confirmPassword);
        add(verifyPassword);
        add(verifyPassField);
        add(verifyPasswordButton);
        add(changePasswordButton);
        add(deleteAccountButton);
    }
}
