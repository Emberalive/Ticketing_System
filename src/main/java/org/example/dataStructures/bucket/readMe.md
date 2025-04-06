## Why Use a Capacity? What’s the Benefit?

Even though dynamic queues are flexible, a fixed capacity can be helpful in some specific ways -> especially in systems like IT ticketing.

Here’s why:
Protects the system from overload

    You don’t want to take in millions of tickets at once and crash your server.

    Capacity acts like a flood gate — once it’s full, you can reject new tickets, log them, or handle them differently.

Helps with memory control

    Arrays with fixed sizes are memory-efficient and fast.

    No need to reallocate or resize the array like dynamic structures do.

Can help prioritize more clearly

    You might decide:

        “Only 100 tickets can be active at once”

        “Lower priority tickets get dropped if the queue is full”

    That gives your system a clear priority-based discipline, which matters a lot in real-time or time-sensitive systems.

Predictable performance

    Fixed-size arrays mean your queue's performance (insert/remove) is very consistent.

    Dynamic structures might slow down during resizing or garbage collection.