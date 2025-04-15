package org.Emberalive.accountDetailsView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class AccountView extends JFrame {
    private static final Logger logger = LogManager.getLogger(AccountView.class);
    AccountModel model = new AccountModel(this);


    public AccountView(String username) {
        setTitle("Account Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);

        setLayout(null);

        JLabel accountLabel = new JLabel("Account Details: " + username + "!");
        accountLabel.setBounds(50, 10, 250, 30);

        JTextArea accountDetailsArea = new JTextArea();
        accountDetailsArea.setEditable(false);
        accountDetailsArea.setPreferredSize(new Dimension(500, 300));
        accountDetailsArea.setBounds(60, 50, 500, 300);

        JButton getAccountDetails = new JButton("Account Details");
        getAccountDetails.setBounds(50, 360, 100, 30);

        getAccountDetails.addActionListener(e -> {
            //call get account details method
            logger.info("Getting Account Details for user: {}", username);
            String accountDetails = model.getAccountDetails(username);
            if (accountDetails != null) {
                accountDetailsArea.setText(accountDetails);
            } else {
//                JOptionPane.showMessageDialog("There was an error getting your details");
                logger.error("Account details could not be retrieved from the Database");
            }
        });
        add(accountLabel);
        add(getAccountDetails);
        add(accountDetailsArea);
    }
}
