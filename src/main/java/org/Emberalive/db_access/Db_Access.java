package org.Emberalive.db_access;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.Emberalive.ticket.Ticket;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;


public class Db_Access {
    private static final Logger logger = LogManager.getLogger(Db_Access.class);
    private static final HikariDataSource dataSource;
    static Dotenv dotenv = Dotenv.load();

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dotenv.get("JDBC_URL")); // ✅ Replace with your DB
        config.setUsername(dotenv.get("DB_USERNAME")); // ✅ Replace
        config.setPassword(dotenv.get("DB_PASSWORD")); // ✅ Replace

        config.setMaximumPoolSize(5); // Limit number of connections
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000); // 30s
        config.setMaxLifetime(600000); // 10min
        config.setConnectionTimeout(30000); // 30s wait before timing out

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Error with connecting to Database: {}",e);
        }
        return conn;
    }

    public static void shutDown () {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    public void insertTicket(Ticket ticket) {
        logger.info("---- Start insertTicket ----");
        Connection conn = getConnection();
        if (conn != null) {
            try {
                logger.info("Inserting ticket...{}", ticket.getTicketID());
                conn.setAutoCommit(false);

                PreparedStatement stmnt = conn.prepareStatement(
                        "INSERT INTO ticket (ID, issue, priority, status, username, date) VALUES (?, ?, ?, ?, ?, ?)"
                );
                stmnt.setInt(1, ticket.getTicketID());
                stmnt.setString(2, ticket.getIssue());
                stmnt.setInt(3, ticket.getPriority());
                stmnt.setString(4, ticket.getStatus());
                stmnt.setString(5, ticket.getUserID());
                stmnt.setDate(6, Date.valueOf(ticket.getCreationDate()));

                stmnt.executeUpdate();
                conn.commit();

                logger.info("Inserted ticket ID: {}", ticket.loggTicket());
            } catch (SQLException ex) {
                logger.error("Error inserting ticket: {}", ex.getMessage());
                try {
                    conn.rollback();
                    logger.warn("Transaction rolled back.");
                } catch (SQLException rollbackEx) {
                    logger.error("Error during rollback: {}", rollbackEx.getMessage());
                }
            } finally {
                try {
                    conn.close();
                    logger.info("Connection closed.");
                } catch (SQLException close) {
                    logger.error("Error closing connection: {}", close.getMessage());
                }
            }
        } else {
            logger.warn("insertTicket aborted: connection was null.");
        }
        logger.info("---- End insertTicket ----\n");
    }

    public void updateStatusWhenCompleted(int ticketID, String status) {
        logger.info("---- Start updateStatusWhenCompleted [{}] ----", ticketID);
        Connection conn = getConnection();
        if (conn != null) {
            try {
                PreparedStatement stmnt = conn.prepareStatement(
                        "UPDATE ticket SET status = ? WHERE id = ?"
                );
                stmnt.setString(1, status);
                stmnt.setInt(2, ticketID);

                stmnt.executeUpdate();
                conn.commit();
                logger.info("Updated ticket [{}] status to '{}'", ticketID, status);
            } catch (SQLException e) {
                logger.error("Error updating ticket status: {}", e.getMessage());
                try {
                    conn.rollback();
                    logger.warn("Transaction rolled back.");
                } catch (SQLException rollbackEx) {
                    logger.error("Rollback error: {}", rollbackEx.getMessage());
                }
            } finally {
                try {
                    conn.close();
                    logger.info("Connection closed.");
                } catch (SQLException close) {
                    logger.error("Closing connection error: {}", close.getMessage());
                }
            }
        } else {
            logger.warn("updateStatusWhenCompleted aborted: connection was null.");
        }
        logger.info("---- End updateStatusWhenCompleted [{}] ----\n", ticketID);
    }

    public Ticket[] getUserTickets(String username) {
        logger.info("---- Start getUserTickets [{}] ----", username);
        Connection conn = getConnection();
        if (conn != null) {
            try {
                PreparedStatement stmnt = conn.prepareStatement(
                        "SELECT * FROM ticket WHERE username = ? ORDER BY id ASC",
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY
                );
                stmnt.setString(1, username);
                ResultSet rs = stmnt.executeQuery();

                rs.last();
                int rowCount = rs.getRow();
                rs.beforeFirst();

                Ticket[] tickets = new Ticket[rowCount];
                int index = 0;

                if (rowCount > 0) {
                    logger.info("Found {} tickets for user: {}", rowCount, username);
                    while (rs.next()) {
                        Ticket ticket = new Ticket(
                                rs.getString("issue"),
                                rs.getInt("priority"),
                                rs.getString("status"),
                                rs.getString("username"),
                                rs.getString("employee"),
                                rs.getInt("id"),
                                rs.getDate("date").toLocalDate()
                        );
                        logger.info("Retrieved ticket: {}", ticket.loggTicket());
                        tickets[index++] = ticket;
                    }
                }
                rs.close();
                return tickets;
            } catch (SQLException e) {
                logger.error("Database error retrieving user tickets: {}", e.getMessage());
            } finally {
                try {
                    conn.close();
                    logger.info("Connection closed.");
                } catch (SQLException close) {
                    logger.error("Error closing connection: {}", close.getMessage());
                }
            }
        } else {
            logger.warn("getUserTickets aborted: connection was null.");
        }
        logger.info("---- End getUserTickets [{}] ----\n", username);
        return null;
    }

    public Ticket[] getEmployeetickets(String username) {
        logger.info("---- Start getEmployeetickets [{}] ----", username);
        Connection conn = getConnection();
        if (conn != null) {
            try {
                PreparedStatement stmnt = conn.prepareStatement(
                        "SELECT * FROM ticket WHERE status = ? AND employee IN (?, ?) ORDER BY id ASC",
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY
                );
                stmnt.setString(1, "InActive");
                stmnt.setString(2, "Not Assigned");
                stmnt.setString(3, username);

                ResultSet rs = stmnt.executeQuery();

                rs.last();
                int rowCount = rs.getRow();
                rs.beforeFirst();

                Ticket[] tickets = new Ticket[rowCount];
                int index = 0;

                if (rowCount > 0) {
                    logger.info("Found {} tickets for employee: {}", rowCount, username);
                    while (rs.next()) {
                        Ticket ticket = new Ticket(
                                rs.getString("issue"),
                                rs.getInt("priority"),
                                rs.getString("status"),
                                rs.getString("username"),
                                rs.getString("employee"),
                                rs.getInt("id"),
                                rs.getDate("date").toLocalDate()
                        );
                        logger.info("Retrieved ticket: {}", ticket.loggTicket());
                        tickets[index++] = ticket;
                    }
                }
                rs.close();
                return tickets;

            } catch (SQLException e) {
                logger.error("Database error retrieving employee tickets: {}", e.getMessage());
            } finally {
                try {
                    conn.close();
                    logger.info("Connection closed.");
                } catch (SQLException close) {
                    logger.error("Error closing connection: {}", close.getMessage());
                }
            }
        } else {
            logger.warn("getEmployeetickets aborted: connection was null.");
        }
        logger.info("---- End getEmployeetickets [{}] ----\n", username);
        return null;
    }

    public static String hashPassword(String password) {
        logger.info("Hashing password...");
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
}
