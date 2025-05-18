package org.Emberalive.user.DeleteTicket;

import org.Emberalive.ticket.Ticket;
import org.Emberalive.db_access.Db_Access;
import org.Emberalive.user.UserView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DeleteTicketModel {
    DeleteTicketView deleteTicketView;
    private static final Logger logger = LogManager.getLogger(DeleteTicketModel.class);

    public DeleteTicketModel(int ticketID, String status, String username, UserView userView) {
        this.deleteTicketView = new DeleteTicketView(this, ticketID, username, status, userView);
    }

    public void startGUI () {
        this.deleteTicketView.setVisible(true);
    }

    public Ticket getTicket(String username, int ticketID) {
        Ticket ticket = null;

        try (Connection conn = Db_Access.getConnection();
             PreparedStatement stmnt = conn.prepareStatement(
                     "SELECT * FROM ticket WHERE id = ? AND username = ?",
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY
             )){

            stmnt.setInt(1, ticketID);
            stmnt.setString(2, username);

            try (ResultSet rs = stmnt.executeQuery()) {
                if (rs.next()) { // Only move to next if there is a result
                    String issue = rs.getString("issue");
                    int priority = rs.getInt("priority");
                    String status = rs.getString("status");
                    String user = rs.getString("username");
                    LocalDate date = rs.getDate("date").toLocalDate();
                    String employee = rs.getString("employee");

                    ticket = new Ticket(issue, priority, status, user, employee, ticketID, date);
                } else {
                    logger.info("No ticket found with ID: {} for user: {}", ticketID, username);
                    logger.info("OR");
                    logger.info("checking if a deleted ticket has been deleted");
                }
            }

        } catch (SQLException e) {
            logger.error("Database error while fetching ticket ID: {}", ticketID, e);
        }

        return ticket;
    }


    public static void deleteTicket(int ticketID, String status, String username, UserView userView) {
        Connection conn = Db_Access.getConnection();
        if (conn != null) {
            logger.info("Attempting to deleting ticket: {}", ticketID);
            try {
                PreparedStatement stmnt = conn.prepareStatement("SELECT * FROM ticket WHERE id = ? AND status = ?");
                stmnt.setInt(1, ticketID);
                stmnt.setString(2, status);

                ResultSet rs = stmnt.executeQuery();

                while (rs.next()) {
                    String ticketStatus = rs.getString(4);
                    if (ticketStatus.equals(status)) {
                        try {
                            PreparedStatement ps = conn.prepareStatement("DELETE FROM ticket WHERE id = ? AND status = ?");
                            ps.setInt(1, ticketID);
                            ps.setString(2, status);
                            int rowsEffected = ps.executeUpdate();
                            if (rowsEffected == 1) {
                                conn.commit();
                                logger.info("Ticket {} has been deleted", ticketID);
//                                userView.updateUserTickets(username);
                            } else {
                                logger.info("Found more than one ticket in the Database with ID: {} and Status: {}", ticketID, status);
                                conn.rollback();
                            }
                        } catch (SQLException delete) {
                            logger.error("Error Deleting ticket: {} Error: {}", ticketID, delete.getMessage());
                        }
                    }
                }
            } catch (SQLException e) {
                logger.error("Database Error, deleting ticket: {}", e.getMessage());
            } finally {
                try {
                    conn.close();
                } catch (SQLException close) {
                    logger.error("Database error on closing connection: {}", close.getMessage());
                }
            }
        }
    }
}
