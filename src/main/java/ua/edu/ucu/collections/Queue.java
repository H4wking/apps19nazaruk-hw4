package ua.edu.ucu.collections;

import ua.edu.ucu.collections.immutable.ImmutableLinkedList;

public class Queue {
    private ImmutableLinkedList queue;
    private int size;

    public Queue() {
        queue = new ImmutableLinkedList();
    }

    public Object peek() {
        return queue.getFirst();
    }

    public Object dequeue() {
        Object el = peek();
        queue = queue.removeFirst();
        size--;
        return el;
    }

    public void enqueue(Object e) {
        queue = queue.addLast(e);
        size++;
    }

    public int size() {
        return size;
    }
}
