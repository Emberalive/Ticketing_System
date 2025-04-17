package org.Emberalive.user.DeleteTicket;

import org.Emberalive.ticket.Ticket;
import org.Emberalive.user.UserView;
import javax.swing.*;
import java.awt.*;

public class DeleteTicketView extends JFrame {
    DeleteTicketModel model;
    public DeleteTicketView(DeleteTicketModel model, int ticketID, String username, String status, UserView userView) {
        this.model = model;
        setTitle("Delete Ticket ID: " + ticketID);
        setSize(350, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel deleteTicketLabel = new JLabel("Are you sure you want to delete this ticket?");
        deleteTicketLabel.setBounds(10, 10, 340, 30);

        JTextArea ticketDetails = new JTextArea();
        ticketDetails.setBounds(10, 50, 340, 150);
        ticketDetails.setPreferredSize(new Dimension(300, 200));
        ticketDetails.setEditable(false);

        JButton deleteTicket = new JButton("Delete Ticket");
        deleteTicket.setBounds(10, 210, 150, 30);
        deleteTicket.addActionListener( e -> {
            model.deleteTicket(ticketID, status, username, userView);

            Ticket deletedTicket = model.getTicket(username, ticketID);
            if (deletedTicket != null) {
                JOptionPane.showMessageDialog(DeleteTicketView.this, "Ticket has not been deleted");
            } else {
                JOptionPane.showMessageDialog(DeleteTicketView.this, "Ticket has been successfully deleted");
                this.dispose();
                String[] listView = userView.updateUserTickets(username);

                userView.setListView(listView);
            }
        });

        ticketDetails.setText(model.getTicket(username, ticketID).printTicket());

        add(deleteTicketLabel);
        add(deleteTicket);
        add(ticketDetails);
    }
}
