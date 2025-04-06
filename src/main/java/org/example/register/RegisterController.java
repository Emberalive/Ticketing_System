package org.example.register;

public class RegisterController {
    private final RegisterModel model;
    private final RegisterView view;

    public RegisterController(RegisterModel model, RegisterView view) {
        this.model = model;
        this.view = view;
    }
    public void startGUI() {
        view.setVisible(true);
    }
}
