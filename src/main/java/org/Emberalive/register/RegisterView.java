package org.Emberalive.register;

import org.Emberalive.account.User;

import javax.swing.*;

public class RegisterView extends JFrame {

    private final JTextField usernameField;
    private final JPasswordField passwordField;

    private final RegisterModel model = new RegisterModel(this);
    private RegisterController controller;

    public RegisterView() {
        // Frame setup
        setTitle("Register");
        setSize(400, 250);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLayout(null);

        // UI Components
        JLabel usernameLabel = new JLabel("Username");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField();

        JButton register = new JButton("Create Account");

        // Set bounds
        usernameLabel.setBounds(50, 50, 100, 30);
        usernameField.setBounds(150, 50, 200, 30);

        passwordLabel.setBounds(50, 100, 100, 30);
        passwordField.setBounds(150, 100, 200, 30);

        register.setBounds(150, 150, 150, 30);

        // Add components to frame
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(register);

        // Controller initialization
        controller = new RegisterController(model, this);

        // Action Listener
        register.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            User usr = new User();
            usr.setUsername(username);
            usr.setPassword(password);

            boolean success = model.register(usr.getUsername(), usr.getPassword(), usr.getRole());

            if (success) {
                JOptionPane.showMessageDialog(this, "Registration Successful");
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "User was not inserted - account may exist already with username: " + username);
            }
        });
    }
}

