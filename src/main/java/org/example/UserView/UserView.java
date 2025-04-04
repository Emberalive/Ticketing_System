package org.example.UserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserView extends JFrame {
    private JTextField searchField;
    private JList<String> listView;
    private JLabel userLabel;

    public UserView() {
        // Initialize JFrame
        setTitle("User Dashboard");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sample data (List of items for the side menu)
        String[] items = {"Dashboard", "Profile", "Settings", "Logout", "Help", "Reports", "Analytics"};

        // Left side list
        listView = new JList<>(items);
        listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(listView);
        listScrollPane.setPreferredSize(new Dimension(150, 0));  // Width for the list
        add(listScrollPane, BorderLayout.WEST);

        // Right side panel with a search bar and label
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Search bar
        searchField = new JTextField(20);
        searchField.setToolTipText("Search...");

        // Username label
        userLabel = new JLabel("Welcome, Alice!");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Add components to the top panel
        topPanel.add(searchField);
        topPanel.add(userLabel);
        rightPanel.add(topPanel, BorderLayout.NORTH);

        // Add the right panel to the center
        add(rightPanel, BorderLayout.CENTER);

        // Implement search filter logic
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText().toLowerCase();
                String[] items = {"Dashboard", "Profile", "Settings", "Logout", "Help", "Reports", "Analytics"};
                DefaultListModel<String> filteredListModel = new DefaultListModel<>();
                for (String item : items) {
                    if (item.toLowerCase().contains(searchText)) {
                        filteredListModel.addElement(item);
                    }
                }
                listView.setModel(filteredListModel);
            }
        });

        // Make the frame visible
        setVisible(true);
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
