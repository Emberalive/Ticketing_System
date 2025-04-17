package org.Emberalive.Employee;

import org.Emberalive.Main;
import org.Emberalive.dataStructures.bucket.Bucket_Queue;
import org.Emberalive.db_access.Db_Access;
import org.Emberalive.login.LoginController;
import org.Emberalive.login.LoginModel;
import org.Emberalive.login.LoginView;
import org.Emberalive.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class EmployeeView extends JFrame {

    private static final Logger logger = LogManager.getLogger(EmployeeView.class);

    private final EmployeeModel model = new EmployeeModel();
    private final EmployeeController controller = new EmployeeController(this, model);
    private final Db_Access db = new Db_Access();

    // UI Components
    private final JButton logout = new JButton("Logout");
    private final JButton updateTicket = new JButton("Update Ticket");
    private final JButton getTicketButton = new JButton("Get Ticket");
    private final JButton finishTicket = new JButton("Finish Ticket");
    private final JButton completeTicket = new JButton("Complete Ticket");
    private final JButton inComplete = new JButton("UnFinish");

    private final JTextArea ticketsArea = new JTextArea();
    private final JTextArea activeTicket = new JTextArea();

    private JPanel topPanel;
    private JPanel rightPanel;
    private JPanel bottomPanel;

    private final JLabel activeTicketLabel = new JLabel("Active Ticket:");
    private final JLabel userLabel;

    private JList<String> listView;
    private Ticket unfinishedTicket;

    public EmployeeView(String username) {

        // Configure main JFrame
        setTitle("Employee Dashboard");
        setSize(720, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Label setup
        userLabel = new JLabel("Welcome, " + username + "!");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));

        activeTicketLabel.setBounds(40, 220, 150, 30);
        activeTicketLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        // Ticket TextArea
        ticketsArea.setEditable(false);
        ticketsArea.setPreferredSize(new Dimension(300, 200));
        ticketsArea.setBounds(40, 50, 300, 120);
        ticketsArea.setText("Ticket Details");

        // Active Ticket TextArea
        activeTicket.setEditable(false);
        activeTicket.setPreferredSize(new Dimension(300, 120));
        activeTicket.setBounds(40, 260, 300, 120);

        // Button bounds
        updateTicket.setBounds(35, 10, 150, 30);
        getTicketButton.setBounds(195, 10, 150, 30);
        finishTicket.setBounds(190, 390, 150, 30);
        completeTicket.setBounds(35, 180, 150, 30);
        inComplete.setBounds(195, 180, 150, 30);

        // Initial active ticket state
        if (unfinishedTicket != null) {
            logger.info("Setting the unfinishedTicket {}", unfinishedTicket.loggTicket());
            activeTicket.setText(unfinishedTicket.printTicket());
            inComplete.setEnabled(false);
            completeTicket.setEnabled(false);
        } else {
            activeTicket.setText("No active ticket");
            finishTicket.setEnabled(false);
        }

        // List setup
        listView = new JList<>(updateTickets(username));
        listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(listView);
        listScrollPane.setPreferredSize(new Dimension(340, 0));
        listView.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                return label;
            }
        });

        // Panels
        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(logout);
        topPanel.add(userLabel);

        bottomPanel = new JPanel(null);
        bottomPanel.setPreferredSize(new Dimension(370, 500));
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        bottomPanel.add(updateTicket);
        bottomPanel.add(activeTicket);
        bottomPanel.add(getTicketButton);
        bottomPanel.add(ticketsArea);
        bottomPanel.add(completeTicket);
        bottomPanel.add(inComplete);
        bottomPanel.add(finishTicket);
        bottomPanel.add(activeTicketLabel);

        rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(topPanel, BorderLayout.NORTH);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(listScrollPane, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // Action Listeners
        completeTicket.addActionListener(e -> {
            Bucket_Queue bucket = Main.getBucket();
            Ticket ticket = bucket.peek();

            if (ticket == null) {
                JOptionPane.showMessageDialog(this, "No Ticket found!");
            } else {
                controller.changeStatus("Complete", ticket, username);
                Main.getBucket().dequeue();
                listView.setListData(updateTickets(username));
                ticketsArea.setText("");
            }
        });

        inComplete.addActionListener(e -> {
            if (unfinishedTicket == null) {
                Ticket ticket = Main.getBucket().peek();

                if (ticket == null) {
                    JOptionPane.showMessageDialog(this, "No Ticket found!");
                } else {
                    unfinishedTicket = ticket;
                    Main.getBucket().dequeue();
                    controller.changeStatus("Active", ticket, username);

                    listView.setListData(updateTickets(username));
                    activeTicket.setText(ticket.printTicket());

                    completeTicket.setEnabled(false);
                    inComplete.setEnabled(false);
                    finishTicket.setEnabled(true);
                    ticketsArea.setText("");
                }
            }
        });

        logout.addActionListener(e -> {
            this.dispose();
            LoginModel model = new LoginModel();
            LoginView view = new LoginView();
            LoginController controller = new LoginController(model, view);
            controller.startGUI();
        });

        updateTicket.addActionListener(e -> listView.setListData(updateTickets(username)));

        getTicketButton.addActionListener(e -> {
            Ticket ticket = Main.getBucket().peek();
            if (ticket == null) {
                logger.info("Ticket was not found, searched by: {}", username);
                ticketsArea.setText("Ticket was not found");
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
    }

    public String[] updateTickets(String username) {
        Ticket[] userTicketsList = getTickets(username);

        if (userTicketsList == null) {
            logger.error("No tickets found");
            return new String[]{"There are no tickets"};
        }

        String[] userTickets = new String[userTicketsList.length];
        for (int i = 0; i < userTicketsList.length; i++) {
            Ticket ticket = userTicketsList[i];
            userTickets[i] = "(ID: " + ticket.getTicketID() + ") (Status: " + ticket.getStatus() + ") (User: " + ticket.getUserID() + ")";
        }
        return userTickets;
    }

    public Ticket[] getTickets(String username) {
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