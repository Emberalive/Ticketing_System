package org.example.dataStructures.bucket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Ticket;

public class Bucket_Queue {
    private static final Logger logger = LogManager.getLogger(Bucket_Queue.class);
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
    public Ticket dequeue(Ticket ticket) {
        int priority = ticket.getPriority();
        switch (priority) {
            case 1:
                priority_1.deQueue();
                break;
            case 2:
                priority_2.deQueue();
                break;
            case 3:
                priority_3.deQueue();
                break;
            case 4:
                priority_4.deQueue();
                break;
            default:
                invalid_priority(priority);
                break;
        }

        return ticket;
    }
    public Ticket peek(int priority) {
        Ticket ticket = null;
        switch (priority) {
            case 1:
                ticket =priority_1.peek();
                break;
            case 2:
                ticket =priority_2.peek();
                break;
            case 3:
                ticket =priority_3.peek();
                break;
            case 4:
                ticket =priority_4.peek();
            default:
                print_ifEmpty();
                break;
        }
        return ticket;
    }

    public void print_ifEmpty() {
        logger.warn("There are no tickets to dequeue");
    }

    public void invalid_priority(int priority) {
        logger.warn("Priority {} is not supported", priority);
    }
}
