package org.example;

import org.example.Employee.EmployeeController;
import org.example.Employee.EmployeeModel;
import org.example.Employee.EmployeeView;
import org.example.dataStructures.bucket.Bucket_Queue;
import org.example.login.LoginController;
import org.example.login.LoginModel;
import org.example.login.LoginView;


public class Main {
    private static Bucket_Queue bucket_queue = new Bucket_Queue();

    public static void main(String[] args) {
        bucket_queue = setBucket();

        EmployeeView view = new EmployeeView("Olivia");
        EmployeeModel model = new EmployeeModel();
        EmployeeController controller = new EmployeeController(view, model);
        controller.startGUI();
        // Initialize the model, view, and controller
//        LoginModel model = new LoginModel();
//        LoginView view = new LoginView();
//        LoginController controller = new LoginController(model, view);

        // Start the GUI through the controller
//        controller.startGUI();
    }
    public static Bucket_Queue getBucket() {
        return bucket_queue;
    }

    public static Bucket_Queue setBucket() {
        return Bucket_Queue.fillFromDB(bucket_queue);
    }
}
