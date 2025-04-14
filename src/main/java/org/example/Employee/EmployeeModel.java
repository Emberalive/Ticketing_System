package org.example.Employee;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Main;
import org.example.Ticket;
import org.example.db_access.Db_Access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmployeeModel {
    private static final Logger logger = LogManager.getLogger(EmployeeModel.class);


    public Ticket getActiveTicket(String username) {
        Connection conn = Db_Access.getConnection();
        Ticket ticket = null;
        try {
            PreparedStatement stmnt = conn.prepareStatement("SELECT * FROM ticket WHERE employee = ? AND status = 'Active'",
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            stmnt.setString(1, username);
            ResultSet rs = stmnt.executeQuery();

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();
            if (rowCount == 1) {
                while (rs.next()) {
                    String issue = rs.getString("issue");
                    int ticketID = rs.getInt("id");
                    int priority = rs.getInt("priority");
                    String status = rs.getString("status");
                    String user = rs.getString("username");
                    LocalDate date = rs.getDate("date").toLocalDate();
                    String employee = rs.getString("employee");

                    ticket = new Ticket(issue, priority, status, user, employee, ticketID, date);
                    logger.info("Getting Unfinished Ticket: {} for Employee: {}", ticket.loggTicket(), username);
                }
                return ticket;
            }  else {
                logger.warn("Employee: {} Has more than one Active Ticket", username);
            }
        } catch (SQLException e) {
            logger.error("Error getting un-finishedTickets fom the database for Employee: {} Error: {}", username, e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("Error closing connection: {}", e.getMessage());
            }
        }
        return null;
    }

    public void updateTicketStatus(Ticket ticket, String newStatus, String username) {
        Connection conn = Db_Access.getConnection();
        int ID = ticket.getTicketID();
        logger.info("Updating Ticket: {}'s status to: 'Active'", ID);
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE ticket SET status = ?, employee = ? WHERE id = ? AND username = ?");
            ps.setString(1, newStatus);
            ps.setString(2, username);
            ps.setInt(3, ID);
            ps.setString(4, ticket.getUsername());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                logger.warn("No ticket was updated. Possibly incorrect ID ({}) or username ({})", ID, ticket.getUsername());
            } else {
                logger.info("Successfully updated {} ticket(s)", rowsUpdated);
                ticket.setStatus(newStatus);
                ticket.setEmployee(username);
                conn.commit();
            }
            if (newStatus.equals("Complete")) {
                Main.getBucket().dequeue();
            }
        } catch (SQLException e) {
            logger.warn("Error updating status for ticket {} to '{}': {}", ticket.loggTicket(), newStatus, e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.warn("Error closing connection: {}", e.getMessage());
            }
        }
    }
}
