package org.example.user;

import org.example.Ticket;
import org.example.dataStructures.bucket.Simple_Queue;
import org.example.db_access.Db_Access;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class UserView extends JFrame {
    Db_Access db = new Db_Access();
    private JTextField searchField;
    private JList<String> listView;
    private JLabel userLabel;

    public UserView(String username) {
        // Initialize JFrame
        setTitle("User Dashboard");
        setSize(720, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //list that holds the users Tickets
        Ticket[] userTicketsList = db.getUserTickets(username);
        //list that holds the users ticket data
        String[] userTickets = new String[userTicketsList.length];

        for (int i = 0; i < userTicketsList.length; i++) {
            //getting the ticket
            Ticket ticket = userTicketsList[i];
            //getting ticket data
            int userID = ticket.getTicketID();
            String status = ticket.getStatus();
            String employee = ticket.getEmployee();
            //concatenating ticket data
            String ticketForUser = "(ID: " + userID + ") (Status: " + status + ") (Employee: " + employee + ")";
            //adding ticket data to the list that is shown in the GUI
            userTickets[i] = ticketForUser;
        }

        // Left side list
        listView = new JList<>(userTickets);
        listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(listView);
        listScrollPane.setPreferredSize(new Dimension(340, 0));  // Width for the list
        add(listScrollPane, BorderLayout.WEST);
        // Set custom ListCellRenderer with a border
        listView.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                //get the default renderer component
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                //create a border, setting then thickness and the colour
                Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

                //applying the colour
                label.setBorder(border);

                return label;
            }
        });

        // Right side panel with a search bar and label
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

//        // Search bar
//        searchField = new JTextField(20);
//        searchField.setToolTipText("Search...");

        // Username label
        userLabel = new JLabel("Welcome, " + username + "!");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Add components to the top panel
//        topPanel.add(searchField);
        topPanel.add(userLabel);
        rightPanel.add(topPanel, BorderLayout.NORTH);

        // Add the right panel to the center
        add(rightPanel, BorderLayout.CENTER);
    }

    public void setVisibleUI(boolean visible) {
        setVisible(visible);
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JList<String> getListView() {
        return listView;
    }

    public JLabel getUserLabel() {
        return userLabel;
    }
}
