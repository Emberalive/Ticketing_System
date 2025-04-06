package org.example.register;

import org.example.account.User;

import javax.swing.*;

public class RegisterView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    RegisterModel model = new RegisterModel();
    RegisterController controller;

    public RegisterView() {
        setTitle("Register");
        setSize(400, 250);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        //layout and components
        setLayout(null);


        //adding the username label
        JLabel usernameLabel = new JLabel("Username");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField();
        JButton register = new JButton("Create Account");


        usernameLabel.setBounds(50, 50, 100, 30); // x, y, width, height
        usernameField.setBounds(150, 50, 200, 30); // x, y, width, height

        passwordLabel.setBounds(50, 100, 100, 30); // x, y, width, height
        passwordField.setBounds(150, 100, 200, 30); // x, y, width, height

        register.setBounds(150, 150, 150, 30);

        //adding the components
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(register);

        controller = new RegisterController(model, this);

        register.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            User usr = new User();
            usr.setUsername(username);
            boolean success = usr.setPassword(password);
            if (success) {
                JOptionPane.showMessageDialog(this, "Registration Successful");

                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "User was not inserted - account may exists already with username: " + username);
            }
        });

    }
}
