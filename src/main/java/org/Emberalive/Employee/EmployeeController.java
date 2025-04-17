package org.Emberalive.Employee;

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

    public Ticket startActiveTicket(String username) {
        return model.getActiveTicket(username);
    }
}
