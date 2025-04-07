package org.example.dataStructures.bucket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Ticket;

public class Bucket_Queue {
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
        if (!priority_1.isEmpty()) {
            foundTicket = priority_1.searchTicket(ticketID);
            return foundTicket;
        } else if (!priority_2.isEmpty()) {
            foundTicket = priority_2.searchTicket(ticketID);
            return foundTicket;
        } else if (!priority_3.isEmpty()) {
            foundTicket = priority_3.searchTicket(ticketID);
            return foundTicket;
        } else if (!priority_4.isEmpty()) {
            foundTicket = priority_4.searchTicket(ticketID);
            return foundTicket;
        } else {
            log_ifEmpty();
        }
        return null;
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

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
