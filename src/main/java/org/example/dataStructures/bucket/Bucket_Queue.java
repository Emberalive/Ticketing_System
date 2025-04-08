package org.example.dataStructures.bucket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Ticket;
import org.example.db_access.Db_Access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Bucket_Queue {
    Db_Access db = new Db_Access();
    private static final Logger logger = LogManager.getLogger(Bucket_Queue.class);
    private int counter = 0;

    Simple_Queue priority_1 = new Simple_Queue(300);
    Simple_Queue priority_2 = new Simple_Queue(300);
    Simple_Queue priority_3 = new Simple_Queue(300);
    Simple_Queue priority_4 = new Simple_Queue(300);

    public void enqueue(Ticket ticket) {
        int priority = ticket.getPriority();
        switch (priority) {
            case 1:
                priority_1.enQueue(ticket);
                break;
            case 2:
                priority_2.enQueue(ticket);
                break;
            case 3:
                priority_3.enQueue(ticket);
                break;
            case 4:
                priority_4.enQueue(ticket);
                break;
            default:
                invalid_priority(priority);
                break;
        }
    }

    public void dequeue() {
        if (!priority_1.isEmpty()) {
            priority_1.deQueue();
        } else if (!priority_2.isEmpty()) {
            priority_2.deQueue();
        } else if (!priority_3.isEmpty()) {
            priority_3.deQueue();
        } else if (!priority_4.isEmpty()) {
            priority_4.deQueue();
        } else {
            log_ifEmpty();
        }
    }
    public void peek() {
        if (!priority_1.isEmpty()) {
            priority_1.peek();
        } else if (!priority_2.isEmpty()) {
            priority_2.peek();
        } else if (!priority_3.isEmpty()) {
            priority_3.peek();
        } else if (!priority_4.isEmpty()) {
            priority_4.peek();
        } else {
            log_ifEmpty();
        }
    }

    public Ticket searchTicket(int ticketID) {
        Ticket foundTicket = null;
        logger.info("Searching Ticket {}", ticketID);

        foundTicket = priority_1.searchTicket(ticketID);
        if (foundTicket != null) {return foundTicket;}

        foundTicket = priority_2.searchTicket(ticketID);
        if (foundTicket != null) {return foundTicket;}

        foundTicket = priority_3.searchTicket(ticketID);
        if (foundTicket != null) {return foundTicket;}

        foundTicket = priority_4.searchTicket(ticketID);
        if (foundTicket != null) {return foundTicket;}

        log_ifEmpty();
        return null;
    }

    public void setCounterIntial () {
        Connection conn = db.getConnection();
        if (conn != null) {
            try {
                PreparedStatement stmnt = conn.prepareStatement("SELECT MAX(id) FROM ticket");
                conn.setAutoCommit(false);

                ResultSet rs = stmnt.executeQuery();
                if (rs.next()) {
                    this.counter = rs.getInt(1);
                }
                rs.close();
            } catch (SQLException ex) {
                logger.error("Database error, Getting Counter: {}", String.valueOf(ex));
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    logger.error("Database Error closing Connection: {}", String.valueOf(ex));
                }
            }
        }
    }

    public Bucket_Queue fillFromDB() {
        Bucket_Queue dataStruct = new Bucket_Queue();
        Connection conn = db.getConnection();
        if (conn != null) {
            try {
                PreparedStatement stmnt = conn.prepareStatement("SELECT * FROM ticket");

                ResultSet rs = stmnt.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String issue = rs.getString("issue");
                    int priority = rs.getInt("priority");
                    String status = rs.getString("status");
                    String username = rs.getString("username");
                    LocalDate date = rs.getDate("date").toLocalDate();
                    String employee =rs.getString("employee");

                    Ticket ticket = new Ticket(issue, priority, status, username, employee, id, date, dataStruct);
                    dataStruct.enqueue(ticket);
                }
            } catch (SQLException exc) {
                logger.error("Database Error: {}", String.valueOf(exc));
            }
        }
        return dataStruct;
    }

    public void log_ifEmpty() {
        logger.warn("There are no tickets to dequeue");
    }

    public void invalid_priority(int priority) {
        logger.warn("Priority {} is not supported", priority);
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter (int counter) {
        this.counter = counter;
    }
    }
