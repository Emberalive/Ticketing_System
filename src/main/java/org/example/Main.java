package org.example;

import org.example.dataStructures.bucket.Bucket_Queue;
import org.example.login.LoginController;
import org.example.login.LoginModel;
import org.example.login.LoginView;


public class Main {
    private final Bucket_Queue bucket_queue = new Bucket_Queue();

    public static void main(String[] args) {
        // Initialize the model, view, and controller
        LoginModel model = new LoginModel();
        LoginView view = new LoginView();
        LoginController controller = new LoginController(model, view);

        // Start the GUI through the controller
        controller.startGUI();
    }

    public Bucket_Queue getBucket() {
        return bucket_queue.fillFromDB();
    }
}
