package org.example.db_access;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Ticket;

import java.sql.*;
import java.time.LocalDate;

public class Db_Access {
    private static final Logger logger = LogManager.getLogger(Db_Access.class);
    private static final String URL = "jdbc:postgresql://86.19.219.159:5432/itticketing";
    private static final String USER = "samuel";
    private static final String PASSWORD = "QwErTy1243!";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (conn != null && conn.isValid(2)) {
                logger.info("Database connection established!");
                conn.setAutoCommit(false);
            } else {
                logger.info("Database connection not established!");
            }
        } catch (SQLException e) {
            logger.error("Database error: {}", String.valueOf(e));
        }
        return conn;
    }

    public void insertTicket(Ticket ticket) {
        Connection conn = getConnection();
        int ticketID = ticket.getTicketID();
        String issue = ticket.getIssue();
        int priority = ticket.getPriority();
        String status = ticket.getStatus();
        String username = ticket.getUserID();
        LocalDate date = ticket.getCreationDate();
        String employee = ticket.getEmployee();
        if (conn != null) {
            try {
                logger.info("Inserting ticket...{}", ticketID);
                conn.setAutoCommit(false);

                PreparedStatement stmnt = conn.prepareStatement("INSERT INTO ticket (ID, issue, priority, status, username, date, employee) VALUES (?, ?, ?, ?, ?, ?, ?)");
                stmnt.setInt(1, ticketID);
                stmnt.setString(2, issue);
                stmnt.setInt(3, priority);
                stmnt.setString(4, status);
                stmnt.setString(5, username);
                stmnt.setDate(6, Date.valueOf((date)));
                stmnt.setString(7, employee);
                stmnt.executeUpdate();
                conn.commit();

                logger.info("Inserted ticket ID: {}", ticket.printTicket());
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    logger.error(ex1.getMessage());
                }
            } finally {
                try {
                    conn.close();
                } catch (SQLException close) {
                    logger.error(close.getMessage());
                }
            }
        }
    }

    //This method updates the status of a ticket using its id, by searching it in the database,this is so that when a ticket has been completed the user can see that the ticket
    //has been completed even though it is not in the Bucket_Queue
    public void updateStatusWhenCompleted (int ticketID, String status) {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                PreparedStatement stmnt = conn.prepareStatement("UPDATE ticket SET status = ? WHERE id = ?");
                stmnt.setString(1, status);
                stmnt.setInt(2, ticketID);
                stmnt.executeUpdate();
                conn.commit();
                logger.info("Updated ticket: {}'s status to: '{}'", ticketID, status);

            } catch (SQLException e) {
                try{
                    conn.rollback();
                } catch (SQLException ex1) {
                    logger.error("Database Error on rollback: {}", ex1.getMessage());
                }
                logger.error("Database Error when updating ticket Status: {}", e.getMessage());
            } finally {
                try{
                    conn.close();
                } catch (SQLException close) {
                    logger.error("Database Error on closing connection: {}", close.getMessage());
                }
            }
        }
    }

}
