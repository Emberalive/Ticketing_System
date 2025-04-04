package org.example.UserView;

import javafx.application.Application;
import org.example.LoginView.Login;

public class UserController {
    private final UserModel model;
    private final UserView view;

    public UserController(UserModel model, UserView view) {
        this.view = view;
        this.model = model;
    }

    public void startGUI() {
        view.setVisibleUI(true);
    }
}
