package org.Emberalive.Employee;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.Emberalive.ticket.Ticket;
import org.Emberalive.db_access.Db_Access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeModel {
    private static final Logger logger = LogManager.getLogger(EmployeeModel.class);

    public Ticket getActiveTicket(String username) {
        logger.info("---- Start getActiveTicket [{}] ----", username);
        Connection conn = Db_Access.getConnection();
        Ticket ticket = null;

        if (conn == null) {
            logger.warn("getActiveTicket aborted: DB connection was null for user: {}", username);
            return null;
        }

        try {
            PreparedStatement stmnt = conn.prepareStatement(
                    "SELECT * FROM ticket WHERE employee = ? AND status = 'Active'",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            stmnt.setString(1, username);
            ResultSet rs = stmnt.executeQuery();

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            if (rowCount == 1) {
                logger.info("1 active ticket found for employee: {}", username);
                while (rs.next()) {
                    ticket = new Ticket(
                            rs.getString("issue"),
                            rs.getInt("priority"),
                            rs.getString("status"),
                            rs.getString("username"),
                            rs.getString("employee"),
                            rs.getInt("id"),
                            rs.getDate("date").toLocalDate()
                    );
                    logger.info("Retrieved ticket: {}", ticket.loggTicket());
                }
            } else if (rowCount == 0) {
                logger.info("No active tickets found for employee: {}", username);
            } else {
                logger.warn("Employee: {} has {} active tickets (expected 1)", username, rowCount);
            }
        } catch (SQLException e) {
            logger.error("SQL error retrieving active ticket for {}: {}", username, e.getMessage());
        } finally {
            try {
                conn.close();
                logger.info("Connection closed for getActiveTicket");
            } catch (SQLException e) {
                logger.error("Error closing connection in getActiveTicket: {}", e.getMessage());
            }
        }

        logger.info("---- End getActiveTicket [{}] ----\n", username);
        return ticket;
    }

    public void updateTicketStatus(Ticket ticket, String newStatus, String username) {
        logger.info("---- Start updateTicketStatus [{} -> '{}'] ----", ticket.getTicketID(), newStatus);
        Connection conn = Db_Access.getConnection();

        if (conn == null) {
            logger.warn("updateTicketStatus aborted: DB connection was null for user: {}", username);
            return;
        }

        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE ticket SET status = ?, employee = ? WHERE id = ?"
            );
            ps.setString(1, newStatus);
            ps.setString(2, username);
            ps.setInt(3, ticket.getTicketID());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                logger.warn("No ticket updated. Possible invalid ticket ID [{}] or user [{}]", ticket.getTicketID(), username);
            } else {
                conn.commit();
                logger.info("Ticket [{}] status updated to '{}' by {}", ticket.getTicketID(), newStatus, username);
                ticket.setStatus(newStatus);
                ticket.setEmployee(username);
            }
        } catch (SQLException e) {
            logger.warn("SQL error updating ticket [{}] to '{}': {}", ticket.loggTicket(), newStatus, e.getMessage());
        } finally {
            try {
                conn.close();
                logger.info("Connection closed for updateTicketStatus");
            } catch (SQLException e) {
                logger.warn("Error closing connection in updateTicketStatus: {}", e.getMessage());
            }
        }
        logger.info("---- End updateTicketStatus [{} -> '{}'] ----\n", ticket.getTicketID(), newStatus);
    }
}
