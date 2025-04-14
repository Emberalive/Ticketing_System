package org.Emberalive.dataStructures.priority_queue;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.Emberalive.Ticket;

public class Priority_Queue_List {
    private static final Logger logger = LogManager.getLogger(Priority_Queue_List.class);

    private final Ticket[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 1000;

    public Priority_Queue_List() {
        this.heap = new Ticket[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }

    public void add(Ticket ticket) {
        if (size >= DEFAULT_CAPACITY) {
            logger.warn("Priority queue is full");
            return;
        }
        heap[size] = ticket;
        bubbleUp(size);
        size++;
    }

    public Ticket peek() {
        if (isEmpty()) {
            return null;
        }
        return heap[0];
    }
    public Ticket poll() {
        if (isEmpty()) {
            logger.warn("Priority queue is empty");
            return null;
        }
        Ticket top  = heap[0];
        heap[0] = heap[--size];
        size--;
        bubbleDown(0);
        return top;
    }

    private void bubbleUp(int index) {
        //checks if the index is not the top of the queue and the index are not null
        while (index > 0 && heap[index] != null) {
            // get the parent node - Parent of node i is at (i - 1) / 2
            int ParentIndex = (index -1) /2;
            //checks if the current ticket haas a higher priority thhann its parent
            if (heap[index].getPriority() < heap[ParentIndex].getPriority()) {
                //if it  is swap the indexes until it is
                swap(index, ParentIndex);
                //updating the index so that it is the new parent index
                index = ParentIndex;
            } else {
                //if it's not beak the while loop
                break;
            }
        }
    }

    private void bubbleDown(int index) {
        //While the current node has at least a left child, keep checking (no need to go further if it's a leaf)
        while (index * 2 + 1 < size) {
            //Get the indices of the left and right children.
            int leftChildIndex = index * 2 + 1;
            int rightChildIndex = index * 2 + 2;
            //Assume left child is smaller (higher priority) for now.
            int smallest = leftChildIndex;
            //If the right child exists and has a higher priority (smaller number), update smallest.
            if (rightChildIndex < size && heap[rightChildIndex].getPriority() < heap[smallest].getPriority()) {
                smallest = rightChildIndex;
            }
            //If the smaller child has higher priority than the current one...
            if (heap[smallest].getPriority() > heap[index].getPriority()) {
                //Swap them move the current item down.
                swap(index, smallest);
                //Update index and continue the process with the new position.
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int index1, int index2) {
        //storing the first index in a temp variable
        Ticket temp = heap[index1];
        //assigning the second index value to index 1
        heap[index1] = heap[index2];
       // using the temp variable to assign the value of index 2
        heap[index2] = temp;
    }
}
