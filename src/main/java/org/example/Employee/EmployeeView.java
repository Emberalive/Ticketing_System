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
    private Ticket unfinishedTicket;
    EmployeeModel model = new EmployeeModel();
    EmployeeController controller = new EmployeeController(this, model);
    private static final Logger logger = LogManager.getLogger(EmployeeView.class);
    Db_Access db = new Db_Access();
    private JList<String> listView;
    private final JLabel userLabel;

    public EmployeeView(String username) {
        unfinishedTicket = controller.startActiveTicket(username);

        // Initialize JFrame
        setTitle("Employee Dashboard");
        setSize(720, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //setting up my JComponents
        JButton logout = new JButton("Log out");



        // Username label
        userLabel = new JLabel("Welcome, " + username + "!");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));

        //update tickets
        JButton updateTicket = new JButton("Update Tickets");
        updateTicket.setBounds(35, 10, 150, 30);
//        updateTicket.setFont(new Font("Arial", Font.BOLD, 14));



        //ticket display area
        JTextArea ticketsArea = new JTextArea();
        ticketsArea.setEditable(false);
        ticketsArea.setPreferredSize(new Dimension(300, 200));
        ticketsArea.setBounds(40, 50, 300, 120);
        ticketsArea.setText("Ticket Details");

        JButton getTicketButton = new JButton("Get Tickets");
        getTicketButton.setBounds(195, 10, 150, 30);


        //active tickets            180
        JLabel activeTicketLabel = new JLabel("Active Ticket:");
        activeTicketLabel.setBounds(40, 220, 150, 30);
        activeTicketLabel.setBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK)
        );

        JButton finishTicket = new JButton("Finish Ticket");
        finishTicket.setBounds(190, 390, 150, 30);


        JTextArea activeTicket = new JTextArea();
        activeTicket.setEditable(false);
        activeTicket.setPreferredSize(new Dimension(300, 120));
        activeTicket.setBounds(40, 260, 300, 120);

        //complete ticket
        JButton completeTicket = new JButton("Completed");
        completeTicket.setBounds(35, 180, 150, 30);

        completeTicket.addActionListener( e -> {
            String operation = "Complete";
            logger.info("Completing ticket");

            ticketsArea.setText("");


            Bucket_Queue bucket = Main.getBucket();

            Ticket ticket = bucket.peek();
            controller.changeStatus(operation, ticket, username);
            listView.setListData(updateTickets(username));
        });

        //unfinished tickets
        JButton inComplete = new JButton("Unfinished");
        inComplete.setBounds(195, 180, 150, 30);


        inComplete.addActionListener(e -> {
            Bucket_Queue bucket = Main.getBucket();
            if (unfinishedTicket == null) {

                Ticket ticket = bucket.peek();
                bucket.dequeue();
                unfinishedTicket = ticket;

                String operation = "Active";


                controller.changeStatus(operation, ticket, username);
                ticketsArea.setText("");
                listView.setListData(updateTickets(username));

                activeTicket.setText(ticket.printTicket());

                completeTicket.setEnabled(false);
                inComplete.setEnabled(false);
                finishTicket.setEnabled(true);
            }
        });


        //ActionListeners set up
        logout.addActionListener(e -> {
            this.dispose();

            LoginModel model = new LoginModel();
            LoginView view = new LoginView();
            LoginController controller = new LoginController(model, view);

            controller.startGUI();
        });

        updateTicket.addActionListener(e -> {
            listView.setListData(updateTickets(username));
        });


        //checks if the unfinished ticket i null and sets default text if it is
        if (unfinishedTicket != null) {
            logger.info("Setting the unfinishedTicket {}", unfinishedTicket.loggTicket());
            activeTicket.setText(unfinishedTicket.printTicket());
            inComplete.setEnabled(false);
            completeTicket.setEnabled(false);
        } else {
            activeTicket.setText("No active ticket");
        }

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

        finishTicket.addActionListener(e -> {
            controller.changeStatus("Complete", unfinishedTicket, username);
            unfinishedTicket = null;
            listView.setListData(updateTickets(username));

            activeTicket.setText("No Active Ticket");
            finishTicket.setEnabled(false);
            completeTicket.setEnabled(true);
            inComplete.setEnabled(true);
        });


        //setting up the JPanels for the JFrame

        // Right side panel with a search bar and label
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        //top panel that holds the logout button and username
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // setting up the bottomPanel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(370, 500));
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        bottomPanel.setLayout(null);

        // Add components to the top panel
        topPanel.add(logout);
        topPanel.add(userLabel);
        rightPanel.add(topPanel, BorderLayout.NORTH);

        // adding JComponents to the bottom panel
        bottomPanel.add(updateTicket);
        bottomPanel.add(activeTicket);
        bottomPanel.add(getTicketButton);
        bottomPanel.add(ticketsArea);
        bottomPanel.add(completeTicket);
        bottomPanel.add(inComplete);
        bottomPanel.add(finishTicket);
        bottomPanel.add(activeTicketLabel);

        //adding the bottom panel to the bottom layout
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add the right panel to the center
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

    public JList<String> getListView() {
        return listView;
    }

    public JLabel getUserLabel() {
        return userLabel;
    }
}
