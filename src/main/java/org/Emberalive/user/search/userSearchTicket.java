package org.Emberalive.user.search;

import org.Emberalive.Main;
import org.Emberalive.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class userSearchTicket extends JFrame {
    private static final Logger logger = LogManager.getLogger(userSearchTicket.class);

    private final JTextField searchField = new JTextField();
    private final JButton searchButton = new JButton("Search");
    private final JTextArea ticketDisplayArea = new JTextArea();

    public userSearchTicket() {
        // Frame setup
        setTitle("Search Ticket");
        setSize(400, 250);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Search input field
        searchField.setBounds(120, 30, 265, 30);
        add(searchField);

        // Search button
        searchButton.setBounds(20, 30, 90, 30);
        add(searchButton);

        // Ticket display area
        ticketDisplayArea.setBounds(50, 70, 300, 120);
        ticketDisplayArea.setEditable(false);
        add(ticketDisplayArea);

        // Action listener for search
        searchButton.addActionListener(e -> {
            String input = searchField.getText().trim();
            logger.info("Searching for Ticket: {}", input);

            try {
                int ticketID = Integer.parseInt(input);
                Ticket foundTicket = Main.getBucket().searchTicket(ticketID);

                if (foundTicket != null) {
                    logger.info("Found Ticket: {}", foundTicket.loggTicket());
                    ticketDisplayArea.setText(foundTicket.printTicket());
                } else {
                    ticketDisplayArea.setText("Ticket was not found.");
                }
            } catch (NumberFormatException ex) {
                ticketDisplayArea.setText("Invalid Ticket ID.");
            }

            searchField.setText("");
        });
    }
}

