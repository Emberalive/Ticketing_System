package org.Emberalive.user;

import org.Emberalive.Main;
import org.Emberalive.accountDetailsView.AccountModel;
import org.Emberalive.accountDetailsView.AccountView;
import org.Emberalive.db_access.Db_Access;
import org.Emberalive.login.LoginController;
import org.Emberalive.login.LoginModel;
import org.Emberalive.login.LoginView;
import org.Emberalive.ticket.Ticket;
import org.Emberalive.user.DeleteTicket.DeleteTicketModel;
import org.Emberalive.user.search.userSearchTicket;
import org.Emberalive.user.search.userSearchTicketModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class UserView extends JFrame {

    private static final Logger logger = LogManager.getLogger(UserView.class);
    private static int priority = 0;

    private final Db_Access db = new Db_Access();
    private final userSearchTicketModel searchTicketModel = new userSearchTicketModel();

    private userSearchTicket userSearchTicket;
    private JTextField searchField;
    private JList<String> listView;
    private JLabel userLabel;

    public UserView(String username) {
        // Frame setup
        setTitle("User Dashboard");
        setSize(720, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Ticket list panel (left)
        listView = new JList<>(updateUserTickets(username));
        listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listView.setCellRenderer(createBorderedCellRenderer());

        JScrollPane listScrollPane = new JScrollPane(listView);
        listScrollPane.setPreferredSize(new Dimension(340, 0));
        add(listScrollPane, BorderLayout.WEST);

        listView.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleTicketSelection(username);
            }
        });

        // Priority Buttons
        JButton securityIssueBtn = createPriorityButton("Security Issue", 1);
        JButton networkIssueBtn = createPriorityButton("Network Issue", 2);
        JButton appInstallBtn = createPriorityButton("App Installation", 3);
        JButton deviceConfigBtn = createPriorityButton("Device Configuration", 4);

        // Right Panel Layout
        JPanel rightPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        JButton logoutBtn = new JButton("Log out");
        logoutBtn.addActionListener(e -> {
            logger.info("Logging out User: {}", username);
            dispose();
            if (userSearchTicket != null) {
                searchTicketModel.FinishGUI(userSearchTicket);
            } else {
                logger.warn("No open search window to close");
            }
            LoginModel model = new LoginModel();
            LoginView view = new LoginView();
            LoginController controller = new LoginController(model, view);
            controller.startGUI();
        });

        JButton accountBtn = new JButton("Account");
        accountBtn.addActionListener(e -> {
            AccountView accountView = new AccountView(username, this);
            AccountModel accountModel = new AccountModel(accountView);
            accountModel.startGUI();
            logger.info("Opening Account Page for User: {}", username);
        });

        userLabel = new JLabel("Welcome, " + username + "!");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));

        topPanel.add(accountBtn);
        topPanel.add(logoutBtn);
        topPanel.add(userLabel);

        JPanel bottomPanel = new JPanel(null);
        bottomPanel.setPreferredSize(new Dimension(370, 350));

        // Ticket creation form
        JLabel formTitle = new JLabel("Create Tickets");
        formTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        formTitle.setBounds(140, 20, 110, 30);

        JLabel issueLabel = new JLabel("Issue:");
        issueLabel.setBounds(35, 70, 125, 30);

        JTextField issueField = new JTextField();
        issueField.setBounds(95, 70, 200, 30);

        JLabel priorityLabel = new JLabel("Select Issue:");
        priorityLabel.setBounds(35, 120, 125, 30);

        JButton createTicketBtn = new JButton("Create Ticket");
        createTicketBtn.setBounds(10, 300, 200, 30);
        createTicketBtn.setBackground(Color.DARK_GRAY);
        createTicketBtn.setForeground(Color.white);
        createTicketBtn.addActionListener(e -> {
            if (issueField.getText().isEmpty() || priority == 0) {
                JOptionPane.showMessageDialog(this, "You must enter both an issue and priority!");
                return;
            }
            Ticket ticket = new Ticket(issueField.getText(), priority, username, Main.getBucket());
            Main.getBucket().enqueue(ticket);
            listView.setListData(updateUserTickets(username));
            issueField.setText("");
            logger.info("Ticket Added");
            priority = 0;
            resetPriorityButtons(securityIssueBtn, networkIssueBtn, appInstallBtn, deviceConfigBtn);
        });

        JButton searchBtn = new JButton("Search Ticket");
        searchBtn.setBounds(220, 300, 150, 30);
        searchBtn.addActionListener(e -> userSearchTicket = searchTicketModel.StartGUI());

        // Add elements to form
        bottomPanel.add(formTitle);
        bottomPanel.add(issueLabel);
        bottomPanel.add(issueField);
        bottomPanel.add(priorityLabel);
        bottomPanel.add(securityIssueBtn);
        bottomPanel.add(networkIssueBtn);
        bottomPanel.add(appInstallBtn);
        bottomPanel.add(deviceConfigBtn);
        bottomPanel.add(createTicketBtn);
        bottomPanel.add(searchBtn);

        // Combine panels
        rightPanel.add(topPanel, BorderLayout.NORTH);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.CENTER);
    }

    private ListCellRenderer<Object> createBorderedCellRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                return label;
            }
        };
    }

    private void handleTicketSelection(String username) {
        String selected = listView.getSelectedValue();
        if (selected != null) {
            int id = Integer.parseInt(selected.split("ID: ")[1].split("\\)")[0].trim());
            String status = selected.split("Status: ")[1].split("\\)")[0].trim();

            if ("Complete".equalsIgnoreCase(status)) {
                new DeleteTicketModel(id, status, username, this).startGUI();
                logger.info("Opening Delete Confirmation");
            } else {
                JOptionPane.showMessageDialog(this, "This ticket cannot be deleted");
            }
        }
    }

    private JButton createPriorityButton(String text, int assignedPriority) {
        JButton button = new JButton(text);
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setBounds(
                (assignedPriority % 2 == 1) ? 10 : 170,
                (assignedPriority <= 2) ? 150 : 200,
                (assignedPriority == 4) ? 200 : 150,
                30
        );
        button.addActionListener(e -> {
            priority = assignedPriority;
            resetPriorityButtonsExcept(button);
        });
        return button;
    }

    private void resetPriorityButtons(JButton... buttons) {
        for (JButton b : buttons) {
            b.setEnabled(true);
        }
    }

    private void resetPriorityButtonsExcept(JButton activeButton) {
        Component[] components = activeButton.getParent().getComponents();
        for (Component comp : components) {
            if (comp instanceof JButton && comp != activeButton) {
                comp.setEnabled(true);
            }
        }
        activeButton.setEnabled(false);
    }

    public String[] updateUserTickets(String username) {
        Ticket[] userTickets = db.getUserTickets(username);

        if (userTickets == null || userTickets.length == 0) {
            return new String[]{"There are no tickets"};
        }

        String[] result = new String[userTickets.length];
        for (int i = 0; i < userTickets.length; i++) {
            Ticket ticket = userTickets[i];
            result[i] = String.format("(ID: %d) (Status: %s) (Employee: %s)",
                    ticket.getTicketID(), ticket.getStatus(), ticket.getEmployee());
        }
        return result;
    }

    // Getters for possible external use
    public JTextField getSearchField() {
        return searchField;
    }

    public void setListView(String[] data) {
        this.listView.setListData(data);
    }

    public JList<String> getListView() {
        return listView;
    }

    public JLabel getUserLabel() {
        return userLabel;
    }

    public void setVisibleUI(boolean visible) {
        setVisible(visible);
    }
}

