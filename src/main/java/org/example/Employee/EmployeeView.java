package org.example.Employee;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Main;
import org.example.Ticket;
import org.example.dataStructures.bucket.Bucket_Queue;
import org.example.db_access.Db_Access;
import org.example.login.LoginController;
import org.example.login.LoginModel;
import org.example.login.LoginView;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class EmployeeView extends JFrame {
    private static final Logger logger = LogManager.getLogger(org.example.user.UserView.class);
    Db_Access db = new Db_Access();
    private JTextField searchField;
    private JList<String> listView;
    private JLabel userLabel;

    public EmployeeView(String username) {
        // Initialize JFrame
        setTitle("Employee Dashboard");
        setSize(720, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton getTicketButton = new JButton("Get Tickets");
        JTextArea ticketsArea = new JTextArea();
        ticketsArea.setPreferredSize(new Dimension(300, 200));
        JButton finishTicket = new JButton("Completed");

        finishTicket.addActionListener( e -> {
            logger.info("Completing ticket");
            Main.getBucket().dequeue();

            ticketsArea.setText("");

            listView.setListData(updateTickets(username));
        });

        getTicketButton.addActionListener(e -> {
            Bucket_Queue bucket = Main.getBucket();

            Ticket ticket = bucket.peek();
            if (ticket == null) {
                ticketsArea.setText("Ticket was not found");
                logger.info("Ticket was not found, When searched by one by: {} ", username);
            } else {
                ticketsArea.setText(ticket.printTicket());
            }
        });

        getTicketButton.setBounds(70, 50, 80, 30);
        ticketsArea.setBounds(70, 130, 300, 120);
        finishTicket.setBounds(70, 150, 80, 30);

        // Left side list
        listView = new JList<>(updateTickets(username));
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

        JButton logout = new JButton("Log out");
        logout.addActionListener(e -> {
            this.dispose();

            LoginModel model = new LoginModel();
            LoginView view = new LoginView();
            LoginController controller = new LoginController(model, view);

            controller.startGUI();
        });

        // Username label
        userLabel = new JLabel("Welcome, " + username + "!");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Add components to the top panel
        topPanel.add(logout);
        topPanel.add(userLabel);
        rightPanel.add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(370, 350));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton updateTickets = new JButton("Update Tickets");
        updateTickets.setFont(new Font("Arial", Font.BOLD, 14));
        updateTickets.addActionListener(e -> {
            listView.setListData(updateTickets(username));
        });
        bottomPanel.add(updateTickets);

        bottomPanel.add(getTicketButton);
        bottomPanel.add(ticketsArea);
        bottomPanel.add(finishTicket);

        // Add the right panel to the center
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.CENTER);
    }

    public String[] updateTickets(String username) {
        //list that holds the users Tickets
        Ticket[] userTicketsList = getTickets(username);
        //list that holds the users ticket data
        if (userTicketsList == null) {
            logger.error("No tickets found");
            String[] emptyTickets = new String[1];
            emptyTickets[0] = "There are no tickets";
            return emptyTickets;
        } else {
            String[] userTickets = new String[userTicketsList.length];

            for (int i = 0; i < userTicketsList.length; i++) {
                //getting the ticket
                Ticket ticket = userTicketsList[i];
                //getting ticket data
                int userID = ticket.getTicketID();
                String status = ticket.getStatus();
                String user = ticket.getUserID();
                //concatenating ticket data
                String ticketForUser = "(ID: " + userID + ") (Status: " + status + ") (User: " + user + ")";
                //adding ticket data to the list that is shown in the GUI
                userTickets[i] = ticketForUser;
            }
            return userTickets;
        }
    }

    public Ticket[] getTickets (String username) {
        return db.getEmployeetickets(username);
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
