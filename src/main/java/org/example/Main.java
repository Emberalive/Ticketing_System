package org.example;

import org.example.Account.user;
import org.example.Login.LoginController;
import org.example.Login.LoginModel;
import org.example.Login.LoginView;


public class Main {
    public static void main(String[] args) {
        // Initialize the model, view, and controller
        LoginModel model = new LoginModel();
        LoginView view = new LoginView();
        LoginController controller = new LoginController(model, view);

        // Start the GUI through the controller
        controller.startGUI();
    }
}
