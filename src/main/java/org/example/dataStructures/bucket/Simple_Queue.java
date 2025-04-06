package org.example.dataStructures.bucket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Ticket;

public class Simple_Queue {
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
            logger.warn("Queue is full! Cannot enqueue Ticket " + ticket);
            return;
        }
        queue[rear] = ticket;
        rear = (rear + 1) % capacity;
        size++;
    }

    //removing items from the queue
    public void deQueue() {
        if (isEmpty()) {
            logger.warn("Queue is empty! Cannot dequeue.");
            return;
        }
        Ticket ticket = queue[front];
        front = (front + 1) % capacity;
        size--;
        logger.info("Dequeued Ticket {}", ticket);
    }

    //view the  front item without removing it
    public Ticket peek() {
        if (isEmpty()){
            logger.warn("Queue is empty! Cannot peek.");
            return null;
        }
        return queue[front];
    }

    //checks if the queue is emppty
    public boolean isEmpty() {
        return size == 0;
    }

    //checks if the queue is full
    public boolean isFull() {
        return size == capacity;
    }

    //gets the size of the queue
    public int getSize() {
        return size;
    }
}
