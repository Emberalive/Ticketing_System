package org.example.UserView;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class UserView extends Application {
    public void initializeUI (Stage stage) {
        // Sample data
        ObservableList<String> items = FXCollections.observableArrayList(
                "Dashboard", "Profile", "Settings", "Logout", "Help", "Reports", "Analytics"
        );

        // Search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search...");

        // Filtered list
        FilteredList<String> filteredItems = new FilteredList<>(items, p -> true);
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            String lower = newVal.toLowerCase();
            filteredItems.setPredicate(item -> item.toLowerCase().contains(lower));
        });

        // ListView
        ListView<String> listView = new ListView<>(filteredItems);
        listView.setPrefWidth(150);

        // Username label
        Label userLabel = new Label("Welcome, Alice!");
        userLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Top bar with search and username
        HBox topBar = new HBox(10, searchField, userLabel);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(searchField, Priority.ALWAYS);

        // Right-hand side content area
        VBox rightPane = new VBox(10, topBar);
        rightPane.setPadding(new Insets(10));
        rightPane.setAlignment(Pos.TOP_RIGHT);

        // Root layout: Split left and right
        BorderPane root = new BorderPane();
        root.setLeft(listView);
        root.setCenter(rightPane);

        Scene scene = new Scene(root, 500, 300);
        stage.setScene(scene);
        stage.setTitle("JavaFX Dashboard");
        stage.show();
    }
    @Override
    public void start(Stage stage) {
        // Call the UI setup method
        initializeUI(stage);
    }
    public static void main(String[] args) {
        launch();
    }
}
