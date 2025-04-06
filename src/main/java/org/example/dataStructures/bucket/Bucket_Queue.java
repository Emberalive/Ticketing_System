package org.example.dataStructures.bucket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.account.Account;

public class Bucket_Queue {
    private static final Logger logger = LogManager.getLogger(Account.class);
    private int[] queue;  //array that holds the queue
    private int front;  //Points to the front item
    private int rear;   //points to the next insertion point
    private int capacity;   //Max size of the queue
    private int size;   //Cuirrent number of items

    public Bucket_Queue(int capacity) {
        this.capacity = capacity;
        this.queue = new int[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    //Adding items to the queue
    public void enQueue(int value) {
        if (isFull()) {
            logger.warn("Queue is full! Cannot enqueue Ticket " + value);
            return;
        }
        queue[rear] = value;
        rear = (rear + 1) % capacity;
        size++;
    }

    //removing items from the queue
    public int deQueue() {
        if (isEmpty()) {
            logger.warn("Queue is empty! Cannot dequeue.");
            return -1;
        }
        int value = queue[front];
        front = (front + 1) % capacity;
        size--;
        return value;
    }

    //view the  front item without removing it
    public int peek() {
        if (isEmpty()){
            logger.warn("Queue is empty! Cannot peek.");
            return -1;
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
