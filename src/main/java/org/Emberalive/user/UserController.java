package org.Emberalive.user;

public class UserController {
    private final UserView view;

    public UserController(UserView view) {
        this.view = view;
    }

    public void startGUI() {
        view.setVisibleUI(true);
    }
}
