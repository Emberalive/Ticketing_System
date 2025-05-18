package org.Emberalive.Employee;

import org.Emberalive.dataStructures.bucket.Bucket_Queue;
import org.Emberalive.ticket.Ticket;

public class EmployeeController {
    private final EmployeeView view;
    private final EmployeeModel model;


    public EmployeeController(EmployeeView view, EmployeeModel model) {
        this.view = view;
        this.model = model;
    }
    public void startGUI() {
        view.setVisible(true);
    }

    public void changeStatus(String operation, Ticket ticket, String username) {
        model.updateTicketStatus(ticket, operation, username);
    }

    public Bucket_Queue startActiveTicket(String username) {
        return model.getActiveTickets(username);
    }
}
