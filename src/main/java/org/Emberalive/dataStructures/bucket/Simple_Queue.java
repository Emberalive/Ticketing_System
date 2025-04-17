package org.Emberalive.dataStructures.bucket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.Emberalive.ticket.Ticket;
import org.Emberalive.db_access.Db_Access;

public class Simple_Queue {
    Db_Access db = new Db_Access();
    private static final Logger logger = LogManager.getLogger(Simple_Queue.class);
    private final Ticket[] queue;
    private int front;
    private int rear;
    private final int capacity;
    private int size;

    public Simple_Queue(int capacity) {
        this.capacity = capacity;
        this.queue = new Ticket[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    public void enQueue(Ticket ticket) {
        logger.info("---- Start enQueue ----");
        if (isFull()) {
            logger.warn("Queue is full! Cannot enqueue Ticket {}", ticket.loggTicket());
            logger.info("---- End enQueue ----\n");
            return;
        }
        queue[rear] = ticket;
        rear = (rear + 1) % capacity;
        size++;
        logger.info("Enqueued ticket: {}", ticket.loggTicket());
        logger.info("---- End enQueue ----\n");
    }

    public void deQueue() {
        logger.info("---- Start deQueue ----");
        if (isEmpty()) {
            logger.warn("Queue is empty! Cannot dequeue.");
            logger.info("---- End deQueue ----\n");
            return;
        }
        Ticket ticket = queue[front];
        int ticketID = ticket.getTicketID();
//        db.updateStatusWhenCompleted(ticketID, "Completed");

        front = (front + 1) % capacity;
        size--;
        logger.info("Dequeued Ticket {}", ticket.loggTicket());
        logger.info("---- End deQueue ----\n");
    }

    public Ticket peek() {
        logger.info("---- Start peek ----");
        if (isEmpty()) {
            logger.warn("Queue is empty! Cannot peek.");
            logger.info("---- End peek ----\n");
            return null;
        }
        logger.info("Peeking Ticket {}", queue[front].loggTicket());
        logger.info("---- End peek ----\n");
        return queue[front];
    }

    public Ticket searchTicket(int ticketID) {
        logger.info("---- Start searchTicket [{}] ----", ticketID);
        if (isEmpty()) {
            logger.warn("Queue is empty! Cannot search ticket: {}", ticketID);
            logger.info("---- End searchTicket [{}] ----\n", ticketID);
            return null;
        } else {
            for (int i = 0; i < size; i++) {
                int index = (front + i) % capacity;
                Ticket ticket = queue[index];
                if (ticket != null && ticket.getTicketID() == ticketID) {
                    logger.info("Found ticket: {}", ticket.loggTicket());
                    logger.info("---- End searchTicket [{}] ----\n", ticketID);
                    return ticket;
                }
            }
        }
        logger.warn("Ticket not found: {}", ticketID);
        logger.info("---- End searchTicket [{}] ----\n", ticketID);
        return null;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }
}
