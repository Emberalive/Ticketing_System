package org.example.UserView;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserView extends JFrame {
    private JTextField searchField;
    private JList<String> listView;
    private JLabel userLabel;

    public UserView(String username) {
        // Initialize JFrame
        setTitle("User Dashboard");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sample data (List of items for the side menu)
        String[] items = {"ticket 1", "ticket 2", "ticket 3", "ticket 4", "ticket 5", "ticket 6", "ticket 7"};

        // Left side list
        listView = new JList<>(items);
        listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(listView);
        listScrollPane.setPreferredSize(new Dimension(150, 0));  // Width for the list
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

//        // Search bar
//        searchField = new JTextField(20);
//        searchField.setToolTipText("Search...");

        // Username label
        userLabel = new JLabel("Welcome, " + username + "!");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Add components to the top panel
//        topPanel.add(searchField);
        topPanel.add(userLabel);
        rightPanel.add(topPanel, BorderLayout.NORTH);

        // Add the right panel to the center
        add(rightPanel, BorderLayout.CENTER);
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
