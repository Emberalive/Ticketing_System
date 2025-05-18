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
    private Bucket_Queue unfinished_ticket;

    public EmployeeView(String username) {
        logger.info("---- Start EmployeeView constructor [{}] ----", username);

        unfinished_ticket = controller.startActiveTicket(username);
//        unfinishedTicket = model.getActiveTicket(username);

        setTitle("Employee Dashboard");
        setSize(720, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        userLabel = new JLabel("Welcome, " + username + "!");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        activeTicketLabel.setBounds(40, 220, 150, 30);
        activeTicketLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        ticketsArea.setEditable(false);
        ticketsArea.setPreferredSize(new Dimension(300, 200));
        ticketsArea.setBounds(40, 50, 300, 120);
        ticketsArea.setText("Ticket Details");

        activeTicket.setEditable(false);
        activeTicket.setPreferredSize(new Dimension(300, 120));
        activeTicket.setBounds(40, 260, 300, 120);

        updateTicket.setBounds(35, 10, 150, 30);
        getTicketButton.setBounds(195, 10, 150, 30);
        finishTicket.setBounds(190, 390, 150, 30);
        completeTicket.setBounds(35, 180, 150, 30);
        inComplete.setBounds(195, 180, 150, 30);

        if (!unfinished_ticket.isEmpty()) {
            Ticket first_unfinished = unfinished_ticket.peek();
            logger.info("Setting the unfinishedTicket [{}]", first_unfinished.loggTicket());
            activeTicket.setText(first_unfinished.printTicket());
//            inComplete.setEnabled(false);
//            completeTicket.setEnabled(false);
        } else {
            activeTicket.setText("No active ticket");
            finishTicket.setEnabled(false);
        }

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
            logger.info("---- Start completeTicket [{}] ----", username);
            Bucket_Queue bucket = Main.getBucket();
            Ticket ticket = bucket.peek();

            if (ticket == null) {
                JOptionPane.showMessageDialog(this, "No Ticket found!");
            } else {
                controller.changeStatus("Complete", ticket, username);
                bucket.dequeue();
                listView.setListData(updateTickets(username));
                ticketsArea.setText("");
            }
            logger.info("---- End completeTicket [{}] ----\n", username);
        });

        inComplete.addActionListener(e -> {
            logger.info("---- Start inComplete [{}] ----", username);
            Ticket ticket = Main.getBucket().peek();

            if (ticket == null) {
                JOptionPane.showMessageDialog(this, "No Ticket found!");
            } else {
                unfinished_ticket.enqueue(ticket);
                Main.getBucket().dequeue();
                controller.changeStatus("Active", ticket, username);

                listView.setListData(updateTickets(username));
                activeTicket.setText(unfinished_ticket.peek().printTicket());

//                completeTicket.setEnabled(false);
//                inComplete.setEnabled(false);
                finishTicket.setEnabled(true);
                ticketsArea.setText("No ticket Selected");
                logger.info("---- End inComplete [{}] ----\n", username);
            }

        });

        logout.addActionListener(e -> {
            logger.info("---- Start logout [{}] ----", username);
            this.dispose();
            LoginModel model = new LoginModel();
            LoginView view = new LoginView();
            LoginController controller = new LoginController(model, view);
            controller.startGUI();
            logger.info("---- End logout [{}] ----\n", username);
        });

        updateTicket.addActionListener(e -> {
            logger.info("---- Start updateTicket [{}] ----", username);
            listView.setListData(updateTickets(username));
            logger.info("---- End updateTicket [{}] ----\n", username);
        });

        getTicketButton.addActionListener(e -> {
            logger.info("---- Start getTicket [{}] ----", username);
            Ticket ticket = Main.getBucket().peek();
            if (ticket == null) {
                logger.info("Ticket not found for [{}]", username);
                ticketsArea.setText("Ticket was not found");
            } else {
                ticketsArea.setText(ticket.printTicket());
            }
            logger.info("---- End getTicket [{}] ----\n", username);
        });

        finishTicket.addActionListener(e -> {
            logger.info("---- Start finishTicket [{}] ----", username);
            controller.changeStatus("Complete", unfinished_ticket.peek(), username);
            unfinished_ticket.dequeue();

            listView.setListData(updateTickets(username));
            Ticket newest_ticket = unfinished_ticket.peek();
            if (newest_ticket != null) {
                activeTicket.setText(newest_ticket.printTicket());
//                finishTicket.setEnabled(false);
//                completeTicket.setEnabled(true);
                inComplete.setEnabled(true);
            } else {
                activeTicket.setText("No active ticket's");
                finishTicket.setEnabled(false);
            }
            logger.info("---- End finishTicket [{}] ----\n", username);
        });

        logger.info("---- End EmployeeView constructor [{}] ----\n", username);
    }

    public String[] updateTickets(String username) {
        logger.info("---- Start updateTickets [{}] ----", username);
        Ticket[] userTicketsList = getTickets(username);

        if (userTicketsList == null || userTicketsList.length == 0) {
            logger.warn("No tickets found for [{}]", username);
            logger.info("---- End updateTickets [{}] ----\n", username);
            return new String[]{"There are no tickets"};
        }

        String[] userTickets = new String[userTicketsList.length];
        for (int i = 0; i < userTicketsList.length; i++) {
            Ticket ticket = userTicketsList[i];
            userTickets[i] = "(ID: " + ticket.getTicketID() + ") (Status: " + ticket.getStatus() + ") (User: " + ticket.getUserID() + ")";
        }

        logger.info("---- End updateTickets [{}] ----\n", username);
        return userTickets;
    }

    public Ticket[] getTickets(String username) {
        logger.info("---- Start getTickets [{}] ----", username);
        Ticket[] tickets = db.getEmployeetickets(username);
        logger.info("---- End getTickets [{}] ----\n", username);
        return tickets;
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
