package org.example.LoginView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);
    private final LoginModel model;
    private final LoginView view;

    public LoginController(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;
    }

    public void startLogin(String username, String password) { model.login(username, password); }

    public void startGUI() {
        view.setVisible(true);
    }
}
