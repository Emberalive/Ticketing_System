package org.example.Employee;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Ticket;
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
        setSize(720, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());



        // Left side list
        listView = new JList<>(updateTickets());
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
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton updateTickets = new JButton("Update Tickets");
        updateTickets.setFont(new Font("Arial", Font.BOLD, 14));
        updateTickets.addActionListener(e -> {
            listView.setListData(updateTickets());
        });
        bottomPanel.add(updateTickets);

        // Add the right panel to the center
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.CENTER);
    }

    public String[] updateTickets() {
        //list that holds the users Tickets
        Ticket[] userTicketsList = getTickets();
        //list that holds the users ticket data
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

    public Ticket[] getTickets () {
        return db.getAllTickets();
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
