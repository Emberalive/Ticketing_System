# Bucket Queue & Simple Queue

This repository contains implementations of a Bucket Queue and a Simple Queue in Java. The Bucket Queue is used for managing tickets with multiple priority levels, while the Simple Queue is a basic data structure for managing a queue of tickets.
## Overview
### Bucket Queue

The Bucket Queue manages four priority levels (1 to 4), with each priority having its own queue. Tickets are added to the appropriate priority queue, and when dequeuing, the system will process tickets starting from the highest priority.

    Priority 1: Highest priority.

    Priority 4: Lowest priority.

### Simple Queue

The Simple Queue is an array-based queue implementation. It stores tickets and supports basic queue operations such as enqueue (adding tickets), dequeue (removing tickets), and peek (viewing the front item). The queue operates on a first-in-first-out (FIFO) basis.
## Classes
### Bucket_Queue Class

The Bucket_Queue class manages multiple Simple_Queue instances, each corresponding to a specific priority. It provides methods for enqueuing and dequeuing tickets based on their priority.
Methods:

    enqueue(Ticket ticket): Adds a ticket to the appropriate queue based on its priority.

    dequeue(Ticket ticket): Removes a ticket from the appropriate priority queue.

    peek(int priority): Returns the ticket at the front of the specified priority queue without removing it.

    print_ifEmpty(): Logs a warning if there are no tickets in the queue.

    invalid_priority(int priority): Logs a warning when an invalid priority is provided.

### Simple_Queue Class

The Simple_Queue class is an array-based queue that handles the basic queue operations for tickets. It supports enqueuing, dequeuing, peeking, and checking if the queue is empty or full.
Methods:

    enQueue(Ticket ticket): Adds a ticket to the queue.

    deQueue(): Removes the front ticket from the queue.

    peek(): Returns the front ticket without removing it.

    isEmpty(): Checks if the queue is empty.

    isFull(): Checks if the queue is full.

    getSize(): Returns the number of tickets currently in the queue.