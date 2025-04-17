package org.Emberalive.user.search;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.Emberalive.Main;
import org.Emberalive.ticket.Ticket;

import javax.swing.*;

public class userSearchTicket extends JFrame {
    JTextField searchField = new JTextField();
    JButton searchButton = new JButton();
    JTextArea ticketsArea = new JTextArea();

    private static final Logger logger = LogManager.getLogger(userSearchTicket.class);

    public userSearchTicket() {
        setLayout(null);
        setTitle("Search Ticket");

        searchField.setBounds(120, 30, 265, 30);
        searchButton.setText("Search:");
        searchButton.setBounds(20, 30, 90, 30);
        ticketsArea.setBounds(50, 70, 300, 120);
        ticketsArea.setEditable(false);

        searchButton.addActionListener(e -> {
            logger.info("Searching for Ticket: {}", searchField.getText());

            try {
                int ticketID = Integer.parseInt(searchField.getText().trim());
                Ticket foundTicket = Main.getBucket().searchTicket(ticketID);

                if (foundTicket != null) {
                    logger.info("Found Ticket: {}", foundTicket.loggTicket());
                    ticketsArea.setText(foundTicket.printTicket());
                } else {
                    ticketsArea.setText("Ticket was not found");
                }
            } catch (NumberFormatException ex) {
                ticketsArea.setText("Invalid Ticket ID");
            }
            searchField.setText("");
        });

        add(searchButton);
        add(ticketsArea);
        add(searchField);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null); // Optional: center the window
    }

}
