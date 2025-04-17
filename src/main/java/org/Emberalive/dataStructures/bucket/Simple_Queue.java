package org.Emberalive.dataStructures.bucket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.Emberalive.ticket.Ticket;
import org.Emberalive.db_access.Db_Access;

public class Simple_Queue {
    Db_Access db = new Db_Access();
    private static final Logger logger = LogManager.getLogger(Simple_Queue.class);
    private final Ticket[] queue;  //array that holds the queue
    private int front;  //Points to the front item
    private int rear;   //points to the next insertion point
    private final int capacity;   //Max size of the queue
    private int size;   //Current number of items

    public Simple_Queue(int capacity) {
        this.capacity = capacity;
        this.queue = new Ticket[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    //Adding items to the queue
    public void enQueue(Ticket ticket) {
        if (isFull()) {
            logger.warn("Queue is full! Cannot enqueue Ticket {}", ticket.loggTicket());
            return;
        }
        queue[rear] = ticket;
        rear = (rear + 1) % capacity;
        size++;
        logger.info("Enqueued ticket: {}", ticket.loggTicket());
    }

    //removing items from the queue
    public void deQueue() {
        if (isEmpty()) {
            logger.warn("Queue is empty! Cannot dequeue.");
            return;
        }
        Ticket ticket = queue[front];
        //getting the ticket id and using that to update the ticket in the database before it is removed
        int ticketID = ticket.getTicketID();
//        db.updateStatusWhenCompleted(ticketID, "Completed");

        front = (front + 1) % capacity;
        size--;
        logger.info("Dequeued Ticket {}", ticket.loggTicket());
    }

    //view the  front item without removing it
    public Ticket peek() {
        if (isEmpty()){
            logger.warn("Queue is empty! Cannot peek.");
            return null;
        }
        logger.info("Peeking Ticket {}", queue[front].loggTicket());
        return queue[front];
    }

    public Ticket searchTicket(int ticketID) {
        logger.info("Searching Ticket {}", ticketID);
        if (isEmpty()){
            logger.warn("Queue is empty! Cannot search ticket: {}", ticketID);
            return null;
        } else {
            for (int i = 0; i < size; i++) {
                int index = (front + i) % capacity;
                Ticket ticket = queue[index];
                if (ticket != null && ticket.getTicketID() == ticketID) {
                    return ticket;
                }
            }
        }
        logger.warn("Ticket not found: {}", ticketID);
        return null;
    }

    public int getSize() {
        return size;
    }

    //checks if the queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    //checks if the queue is full
    public boolean isFull() {
        return size == capacity;
    }
}
