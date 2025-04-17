package org.Emberalive.Employee;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.Emberalive.Main;
import org.Emberalive.ticket.Ticket;
import org.Emberalive.dataStructures.bucket.Bucket_Queue;
import org.Emberalive.db_access.Db_Access;
import org.Emberalive.login.LoginController;
import org.Emberalive.login.LoginModel;
import org.Emberalive.login.LoginView;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class EmployeeView extends JFrame {
    private Ticket unfinishedTicket;
    EmployeeModel model = new EmployeeModel();
    EmployeeController controller = new EmployeeController(this, model);
    private static final Logger logger = LogManager.getLogger(EmployeeView.class);
    Db_Access db = new Db_Access();

    //creating button variables
    JButton logout = new JButton("Logout");
    JButton updateTicket = new JButton("Update Ticket");
    JButton getTicketButton = new JButton("Get Ticket");
    JButton finishTicket = new JButton("Finish Ticket");
    JButton completeTicket = new JButton("Complete Ticket");
    JButton inComplete = new JButton("UnFinish");

    //creating the textAreas
    JTextArea ticketsArea = new JTextArea();
    JTextArea activeTicket = new JTextArea();

    //creating the JPanels
    JPanel topPanel;
    JPanel rightPanel;
    JPanel bottomPanel;

    //creating the labels
    JLabel activeTicketLabel = new JLabel("Active Ticket:");
    private final JLabel userLabel;


    private JList<String> listView;

    public EmployeeView(String username) {
        //checks if the unfinished ticket is null and sets default text if it is
        if (unfinishedTicket != null) {
            logger.info("Setting the unfinishedTicket {}", unfinishedTicket.loggTicket());
            activeTicket.setText(unfinishedTicket.printTicket());
            inComplete.setEnabled(false);
            completeTicket.setEnabled(false);
        } else {
            activeTicket.setText("No active ticket");
            finishTicket.setEnabled(false);
        }

        // Initialize JFrame
        setTitle("Employee Dashboard");
        setSize(720, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //setting bounds for my JButtons
        updateTicket.setBounds(35, 10, 150, 30);

        getTicketButton.setBounds(195, 10, 150, 30);

        finishTicket.setBounds(190, 390, 150, 30);

        inComplete.setBounds(195, 180, 150, 30);

        completeTicket.setBounds(35, 180, 150, 30);

        //setting up the JLabels
        userLabel = new JLabel("Welcome, " + username + "!");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));

        activeTicketLabel.setBounds(40, 220, 150, 30);
        activeTicketLabel.setBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK)
        );

        //setting up the JTextAreas
        ticketsArea.setEditable(false);
        ticketsArea.setPreferredSize(new Dimension(300, 200));
        ticketsArea.setBounds(40, 50, 300, 120);
        ticketsArea.setText("Ticket Details");

        activeTicket.setEditable(false);
        activeTicket.setPreferredSize(new Dimension(300, 120));
        activeTicket.setBounds(40, 260, 300, 120);


        completeTicket.addActionListener( e -> {
            String operation = "Complete";
            logger.info("Completing ticket");

            ticketsArea.setText("");


            Bucket_Queue bucket = Main.getBucket();

            Ticket ticket = bucket.peek();
            if (ticket == null) {
                JOptionPane.showMessageDialog(this, "No Ticket found!");
            } else {
                controller.changeStatus(operation, ticket, username);
                Main.getBucket().dequeue();
                listView.setListData(updateTickets(username));
            }

        });


        inComplete.addActionListener(e -> {
            Bucket_Queue bucket = Main.getBucket();
            if (unfinishedTicket == null) {

                Ticket ticket = bucket.peek();
                if (ticket == null) {
                    JOptionPane.showMessageDialog(this, "No Ticket found!");
                } else {
                    unfinishedTicket = ticket;

                    Main.getBucket().dequeue();

                    String operation = "Active";


                    controller.changeStatus(operation, ticket, username);
                    ticketsArea.setText("");
                    listView.setListData(updateTickets(username));

                    activeTicket.setText(ticket.printTicket());

                    completeTicket.setEnabled(false);
                    inComplete.setEnabled(false);
                    finishTicket.setEnabled(true);
                }
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
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        //top panel that holds the logout button and username
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // setting up the bottomPanel
        bottomPanel = new JPanel();
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
