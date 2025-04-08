package org.example;

import org.example.dataStructures.bucket.Bucket_Queue;
import org.example.login.LoginController;
import org.example.login.LoginModel;
import org.example.login.LoginView;


public class Main {
    public static void main(String[] args) {
        Bucket_Queue dataStruct = new Bucket_Queue();

        String issue = "erectile dysfunction";
        int priority = 1;
        int ticketID = 1;
        String status = "active";
        String username = "erectile";
        String employee = "bob";

        Ticket ticket = new Ticket(issue, priority, status, username, employee, dataStruct);

        Ticket ticket2 = new Ticket(issue, priority, status, username, employee, dataStruct);

        Ticket ticket3 = new Ticket(issue, priority, status, username, employee, dataStruct);

        Ticket ticket4 = new Ticket(issue, priority, status, username, employee, dataStruct);

        System.out.println("adding the ticket to the queue"+ ticket);
        dataStruct.enqueue(ticket);
        dataStruct.enqueue(ticket2);
        dataStruct.enqueue(ticket3);
        dataStruct.enqueue(ticket4);

        dataStruct.peek();

        Ticket foundTicket = dataStruct.searchTicket(4);

        dataStruct.dequeue();
        System.out.println("dequeue the ticket from the queue" + ticket.printTicket());

//        // Initialize the model, view, and controller
//        LoginModel model = new LoginModel();
//        LoginView view = new LoginView();
//        LoginController controller = new LoginController(model, view);
//
//        // Start the GUI through the controller
//        controller.startGUI();
    }
}
