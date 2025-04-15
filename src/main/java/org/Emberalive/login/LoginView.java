package org.Emberalive.login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.Emberalive.register.RegisterView;

import javax.swing.*;

public class LoginView extends JFrame{
    private static final Logger logger = LogManager.getLogger(LoginView.class);
    private JTextField usernameField;
    private JPasswordField passwordField;
    LoginView view;
    LoginModel model = new LoginModel();
    LoginController controller;

    public LoginView() {
        setTitle("Ticketing System");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //layout and components
        setLayout(null);

        //adding the username label
        JLabel titleLabel = new JLabel("Login:");
        JLabel usernameLabel = new JLabel("Username");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField();
        JButton login = new JButton("Login");
        JButton register = new JButton("Register");

        titleLabel.setBounds(200, 0, 100, 25);

        usernameLabel.setBounds(50, 50, 100, 30); // x, y, width, height
        usernameField.setBounds(150, 50, 200, 30); // x, y, width, height

        passwordLabel.setBounds(50, 100, 100, 30); // x, y, width, height
        passwordField.setBounds(150, 100, 200, 30); // x, y, width, height

        login.setBounds(37, 150, 150, 30);
        register.setBounds(213, 150, 150, 30);

        //adding the components
        add(titleLabel);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(login);
        add(register);


        login.addActionListener(ev ->{
            controller = new LoginController(model, this);
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            controller.startLogin(username, password);
        });

        register.addActionListener(ev ->{
            RegisterView view = new RegisterView();

            view.setVisible(true);
        });
    }
}

