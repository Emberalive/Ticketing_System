package org.example.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Main;
import org.example.Ticket;
import org.example.db_access.Db_Access;
import org.example.login.LoginController;
import org.example.login.LoginModel;
import org.example.login.LoginView;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class UserView extends JFrame {
    private static int priority = 0;
    private static final Logger logger = LogManager.getLogger(UserView.class);
    Db_Access db = new Db_Access();
    private JTextField searchField;
    private JList<String> listView;
    private JLabel userLabel;

    public UserView(String username) {
        // Initialize JFrame
        setTitle("User Dashboard");
        setSize(720, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Left side list
        listView = new JList<>(updateUserTickets(username));
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

        JButton priority1 = new JButton("Security Issue");
        priority1.setBackground(Color.DARK_GRAY);
        priority1.setForeground(Color.white);
        JButton priority2 = new JButton("Network Issue");
        priority2.setBackground(Color.DARK_GRAY);
        priority2.setForeground(Color.white);
        JButton priority3 = new JButton("app installation");
        priority3.setBackground(Color.DARK_GRAY);
        priority3.setForeground(Color.white);
        JButton priority4 = new JButton("Device configuration");
        priority4.setBackground(Color.DARK_GRAY);
        priority4.setForeground(Color.white);

        priority1.addActionListener(e -> {
            priority = 1;
            priority1.setEnabled(false);
            priority2.setEnabled(true);
            priority3.setEnabled(true);
            priority4.setEnabled(true);
        });
        priority2.addActionListener(e -> {
            priority = 2;
            priority1.setEnabled(true);
            priority2.setEnabled(false);
            priority3.setEnabled(true);
            priority4.setEnabled(true);
        });
        priority3.addActionListener(e -> {
            priority = 3;
            priority1.setEnabled(true);
            priority2.setEnabled(true);
            priority3.setEnabled(false);
            priority4.setEnabled(true);
        });
        priority4.addActionListener(e -> {
            priority = 4;
            priority4.setEnabled(false);
            priority1.setEnabled(true);
            priority2.setEnabled(true);
            priority3.setEnabled(true);
        });

        //        priorityField.setBounds(150, 250, 200, 30);

        priority1.setBounds(10, 150, 150, 30);
        priority2.setBounds(170, 150, 150, 30);
        priority3.setBounds(10, 200, 150, 30);
        priority4.setBounds(170, 200, 200, 30);

        // Right side panel with a search bar and label
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton logout = new JButton("Log out");
        logout.addActionListener(e -> {
            logger.info("Logging out User: " + username);
            this.dispose();

           LoginModel model = new LoginModel();
           LoginView view = new LoginView();
           LoginController controller = new LoginController(model, view);

           controller.startGUI();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(null);
        bottomPanel.setPreferredSize(new Dimension(370, 350));

        JLabel title = new JLabel("Create Tickets");
        JLabel issueLabel = new JLabel("Issue:");
        JTextField issueField = new JTextField();
        JLabel priorityLabel = new JLabel("Select Priority:");

        JButton addTicket = new JButton("Create Ticket");
        addTicket.setBackground(Color.DARK_GRAY);
        addTicket.setForeground(Color.white);
        addTicket.addActionListener(e -> {
            if (issueField.getText().isEmpty() || priority == 0) {
                JOptionPane.showMessageDialog(rootPane, "You must enter both an issue and priority!");
            } else {
                String issue = issueField.getText();
                Ticket ticket = new Ticket(issue, priority, username, "Bob", Main.getBucket());
                Main.getBucket().enqueue(ticket);

                //updates the list of tickets with the newly inserted one
                listView.setListData(updateUserTickets(username));

                issueField.setText("");

                logger.info("Adding Ticket");
                priority1.setEnabled(true);
                priority2.setEnabled(true);
                priority3.setEnabled(true);
                priority4.setEnabled(true);

                priority = 0;
            }
        });

        JButton searchTicket = new JButton("Search ticket");
        searchTicket.addActionListener(e -> {
            userSearchTicketModel model = new userSearchTicketModel();
            model.StartGUI();
        });
        searchTicket.setBounds(220, 300, 150, 30);
        bottomPanel.add(searchTicket);

        title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        title.setBounds(140, 20, 110, 30);

        addTicket.setBounds(10, 300, 200, 30);
        issueField.setBounds(95, 70, 200, 30);
//        priorityField.setBounds(150, 250, 200, 30);

        priorityLabel.setBounds(35, 120, 125, 30);
        issueLabel.setBounds(35, 70, 125, 30);

        bottomPanel.add(title);
        bottomPanel.add(priority1);
        bottomPanel.add(priority2);
        bottomPanel.add(priority3);
        bottomPanel.add(priority4);
        bottomPanel.add(issueLabel);
        bottomPanel.add(priorityLabel);
        bottomPanel.add(issueField);
        bottomPanel.add(addTicket);
        // Username label
        userLabel = new JLabel("Welcome, " + username + "!");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Add components to the top panel
        topPanel.add(logout);
        topPanel.add(userLabel);
        rightPanel.add(topPanel, BorderLayout.NORTH);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add the right panel to the center
        add(rightPanel, BorderLayout.CENTER);
    }

    public String[] updateUserTickets(String username) {
        //list that holds the users Tickets
        Ticket[] userTicketsList = db.getUserTickets(username);
        //list that holds the users ticket data

        if (userTicketsList == null) {
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
                String employee = ticket.getEmployee();
                //concatenating ticket data
                String ticketForUser = "(ID: " + userID + ") (Status: " + status + ") (Employee: " + employee + ")";
                //adding ticket data to the list that is shown in the GUI
                userTickets[i] = ticketForUser;
//            updateUserList(username);
            }
            return userTickets;
        }
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
