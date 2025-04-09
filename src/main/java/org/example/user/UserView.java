package org.example.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Main;
import org.example.Ticket;
import org.example.dataStructures.bucket.Bucket_Queue;
import org.example.dataStructures.bucket.Simple_Queue;
import org.example.db_access.Db_Access;
import org.example.login.LoginController;
import org.example.login.LoginModel;
import org.example.login.LoginView;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class UserView extends JFrame {
    private static final Logger logger = LogManager.getLogger(UserView.class);
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


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(null);
        bottomPanel.setPreferredSize(new Dimension(370, 300));

        JLabel issueLabel = new JLabel("Issue");
        JTextField issueField = new JTextField();
        JLabel priorityLabel = new JLabel("Priority");
        JTextField priorityField = new JTextField();
        JButton addTicket = new JButton("Create Ticket");
        addTicket.addActionListener(e -> {
            Main main = new Main();
            String issue = issueField.getText();
            int priority = Integer.parseInt(priorityField.getText());
            Ticket ticket = new Ticket(issue, priority, username, "Bob", main.getBucket());
            main.getBucket().enqueue(ticket);

            //updates the list of tickets with the newly inserted one
            listView.setListData(updateUserTickets(username));

            logger.info("Adding Ticket");
        });

        JLabel searchLabel = new JLabel("Search");
        searchField = new JTextField();
        searchField.setBounds(65, 30, 270, 30);
        searchLabel.setBounds(20, 30, 100, 30);
        addTicket.setBounds(90, 250, 200, 30);
        issueField.setBounds(150, 150, 200, 30);
        priorityField.setBounds(150, 200, 200, 30);

        priorityLabel.setBounds(75, 200, 125, 30);
        issueLabel.setBounds(75, 150, 125, 30);


        bottomPanel.add(searchLabel);
        bottomPanel.add(searchField);
        bottomPanel.add(issueLabel);
        bottomPanel.add(priorityLabel);
        bottomPanel.add(issueField);
        bottomPanel.add(priorityField);
        bottomPanel.add(addTicket);
        // Username label
        userLabel = new JLabel("Welcome, " + username + "!");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Add components to the top panel
        topPanel.add(logout);
//        topPanel.add(searchField);
        topPanel.add(userLabel);
        rightPanel.add(topPanel, BorderLayout.NORTH);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add the right panel to the center
        add(rightPanel, BorderLayout.CENTER);
    }

    public String[] updateUserTickets(String username) {
        //list that holds the users Tickets
        Ticket[] userTicketsList = getUserTickets(username);
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
//            updateUserList(username);
        }
        return userTickets;
    }

    public Ticket[] getUserTickets (String username) {
        return db.getUserTickets(username);
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
