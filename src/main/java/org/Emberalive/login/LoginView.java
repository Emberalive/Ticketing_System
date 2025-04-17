package org.Emberalive.login;

import org.Emberalive.register.RegisterView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class LoginView extends JFrame {

    private static final Logger logger = LogManager.getLogger(LoginView.class);

    private final JTextField usernameField;
    private final JPasswordField passwordField;

    private final LoginModel model = new LoginModel();
    private LoginController controller;

    public LoginView() {
        // Frame setup
        setTitle("Ticketing System");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Components
        JLabel titleLabel = new JLabel("Login:");
        JLabel usernameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Bounds
        titleLabel.setBounds(200, 0, 100, 25);

        usernameLabel.setBounds(50, 50, 100, 30);
        usernameField.setBounds(150, 50, 200, 30);

        passwordLabel.setBounds(50, 100, 100, 30);
        passwordField.setBounds(150, 100, 200, 30);

        loginButton.setBounds(37, 150, 150, 30);
        registerButton.setBounds(213, 150, 150, 30);

        // Add components to frame
        add(titleLabel);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(registerButton);

        // Action Listeners
        loginButton.addActionListener(e -> {
            controller = new LoginController(model, this);
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            controller.startLogin(username, password);
        });

        registerButton.addActionListener(e -> {
            RegisterView registerView = new RegisterView();
            registerView.setVisible(true);
        });
    }
}

