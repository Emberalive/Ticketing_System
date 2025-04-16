package org.Emberalive.accountDetailsView;

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
    AccountModel model = new AccountModel(this);


    public AccountView(String username, UserView userView) {
        setTitle("Account Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);

        setLayout(null);

        JLabel accountLabel = new JLabel("Account Details: " + username + "!");
        accountLabel.setBounds(50, 10, 250, 30);

        JTextArea accountDetailsArea = new JTextArea();
        accountDetailsArea.setEditable(false);
        accountDetailsArea.setPreferredSize(new Dimension(500, 75));
        accountDetailsArea.setBounds(60, 50, 500, 75);

        JButton getAccountDetails = new JButton("Account Details");
        getAccountDetails.setBounds(50, 410, 200, 30);

        getAccountDetails.addActionListener(e -> {
            //call get account details method
            logger.info("Getting Account Details for user: {}", username);
            String accountDetails = model.getAccountDetails(username);
            if (accountDetails != null) {
                accountDetailsArea.setText(accountDetails);
            } else {
                logger.error("Account details could not be retrieved from the Database");
            }
        });

        JLabel changePassLabel = new JLabel("Change Password?");
        changePassLabel.setBounds(50, 135, 175, 30);
        changePassLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        JLabel newPasswordLabel = new JLabel("New Password");
        newPasswordLabel.setBounds(50, 290, 175, 30);

        JPasswordField newPassword = new JPasswordField();
        newPassword.setBounds(190, 290, 175, 30);
        newPassword.setEnabled(false);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setBounds(50, 330, 175, 30);

        JPasswordField confirmPassword = new JPasswordField();
        confirmPassword.setBounds(190, 330, 175, 30);
        confirmPassword.setEnabled(false);

        JLabel verifyPassword = new JLabel("Verify Password");
        verifyPassword.setBounds(50, 175, 175, 30);

        JPasswordField verifyPassField = new JPasswordField();
        verifyPassField.setBounds(175, 175, 175, 30);

        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(175, 370, 175, 30);
        changePasswordButton.setEnabled(false);
        changePasswordButton.addActionListener(e -> {
           logger.info("Changing Password to change password for: {}", username);
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
               } else {
                   JOptionPane.showMessageDialog(AccountView.this, "Password was not changed!");
               }
           }
        });

        JButton deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.setBounds(260, 410, 175, 30);
        deleteAccountButton.addActionListener(e -> {
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
        });

        JButton verifyPasswordButton = new JButton("Verify Password");
        verifyPasswordButton.setBounds(175, 250, 175, 30);
        verifyPasswordButton.addActionListener(e -> {
            logger.info("Verifying Password to change password for: {}", username);
            String verifyPass = String.valueOf(verifyPassField.getPassword());
            if (model.verifyPassword(username, verifyPass)) {
                confirmPassword.setEnabled(true);
                newPassword.setEnabled(true);
                changePasswordButton.setEnabled(true);
                verifyPassField.setText("");
            }
        });

        add(deleteAccountButton);
        add(changePasswordButton);
        add(newPasswordLabel);
        add(newPassword);
        add(confirmPasswordLabel);
        add(confirmPasswordLabel);
        add(confirmPassword);
        add(verifyPassword);
        add(verifyPassField);
        add(verifyPasswordButton);
        add(changePassLabel);
        add(accountLabel);
        add(getAccountDetails);
        add(accountDetailsArea);
    }
}
