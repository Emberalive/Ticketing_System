package org.Emberalive.user.DeleteTicket;

import org.Emberalive.ticket.Ticket;
import org.Emberalive.user.UserView;

import javax.swing.*;

public class DeleteTicketView extends JFrame {
    private final DeleteTicketModel model;

    public DeleteTicketView(DeleteTicketModel model, int ticketID, String username, String status, UserView userView) {
        this.model = model;

        // Frame setup
        setTitle("Delete Ticket ID: " + ticketID);
        setSize(350, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        // Confirmation label
        JLabel confirmationLabel = new JLabel("Are you sure you want to delete this ticket?");
        confirmationLabel.setBounds(10, 10, 340, 30);
        add(confirmationLabel);

        // Ticket details area
        JTextArea ticketDetailsArea = new JTextArea();
        ticketDetailsArea.setBounds(10, 50, 320, 150);
        ticketDetailsArea.setEditable(false);
        ticketDetailsArea.setText(model.getTicket(username, ticketID).printTicket());
        add(ticketDetailsArea);

        // Delete button
        JButton deleteButton = new JButton("Delete Ticket");
        deleteButton.setBounds(10, 210, 150, 30);
        deleteButton.addActionListener(e -> {
            model.deleteTicket(ticketID, status, username, userView);
            Ticket deletedTicket = model.getTicket(username, ticketID);

            if (deletedTicket != null) {
                JOptionPane.showMessageDialog(this, "Ticket has not been deleted.");
            } else {
                JOptionPane.showMessageDialog(this, "Ticket has been successfully deleted.");
                dispose();

                // Refresh ticket list in the UserView
                String[] updatedTickets = userView.updateUserTickets(username);
                userView.setListView(updatedTickets);
            }
        });
        add(deleteButton);
    }
}

