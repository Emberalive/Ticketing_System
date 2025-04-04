package org.example.LoginView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.Arrays;

public class LoginView extends JFrame{
    private static final Logger logger = LogManager.getLogger(LoginView.class);
    private JTextField usernameField;
    private JPasswordField passwordField;
    LoginModel model = new LoginModel();
    LoginController controller;

    public LoginView() {
        setTitle("Ticketing System");
        setSize(400, 250);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        //layout and components
        setLayout(null);

        //adding the username label
        JLabel titleLabel = new JLabel("Login:");
        JLabel usernameLabel = new JLabel("Username");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField();
        JButton login = new JButton("Login");

        titleLabel.setBounds(200, 0, 100, 25);

        usernameLabel.setBounds(50, 50, 100, 30); // x, y, width, height
        usernameField.setBounds(150, 50, 200, 30); // x, y, width, height

        passwordLabel.setBounds(50, 100, 100, 30); // x, y, width, height
        passwordField.setBounds(150, 100, 200, 30); // x, y, width, height

        login.setBounds(150, 150, 150, 30);

        //adding the components
        add(titleLabel);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(login);

        controller = new LoginController(model, this);

        login.addActionListener(ev ->{
            String username = usernameField.getText();
            String password = Arrays.toString(passwordField.getPassword());

            controller.startLogin(username, password);
        });
    }
}

