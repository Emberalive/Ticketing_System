package org.example;

import org.example.dataStructures.bucket.Bucket_Queue;
import org.example.db_access.Db_Access;
import org.example.login.LoginController;
import org.example.login.LoginModel;
import org.example.login.LoginView;


public class Main {
    private final Bucket_Queue bucket_queue = new Bucket_Queue();

    public static void main(String[] args) {


//        String issue = "password reset";
//        int priority = 1;
//        String username = "samm";
//        String employee = "bob";

//        Ticket ticket = new Ticket(issue, priority, username, employee, dataStruct);
//
//        Ticket ticket2 = new Ticket(issue, priority, status, username, employee, dataStruct);
//
//        Ticket ticket3 = new Ticket(issue, priority, status, username, employee, dataStruct);
//
//        Ticket ticket4 = new Ticket(issue, priority, status, username, employee, dataStruct);
//
//        dataStruct.enqueue(ticket);
//        dataStruct.enqueue(ticket2);
//        dataStruct.enqueue(ticket3);
//        dataStruct.enqueue(ticket4);

//        dataStruct.dequeue();

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
