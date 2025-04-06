package org.example.user;

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
