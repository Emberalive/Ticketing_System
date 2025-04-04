package org.example.LoginView;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Login extends Application {

    private TextField usernameField;
    private PasswordField passwordField;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Register");

        // Initialize UI components
        usernameField = new TextField();
        passwordField = new PasswordField();
        Button registerButton = new Button("Create Account");

        // Labels for the username and password fields
        Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");

        // Layout setup using VBox
        VBox vbox = new VBox(10);  // 10px spacing between elements
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, registerButton);

        // Create the scene and set it to the stage
        Scene scene = new Scene(vbox, 400, 250);
        primaryStage.setScene(scene);

        // Show the stage (window)
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}

