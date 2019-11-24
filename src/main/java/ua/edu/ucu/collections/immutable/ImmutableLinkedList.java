package ua.edu.ucu.collections.immutable;

import java.util.Arrays;

public class ImmutableLinkedList implements ImmutableList {
    private static class Node {
        private Object data;
        private Node next;

        private Node(Object data) {
            this.data = data;
            this.next = null;
        }

        private Node(Object data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public ImmutableLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public ImmutableLinkedList(Object[] arr) {
        if (arr.length != 0) {
            head = new Node(arr[0]);
            Node cur = head;
            for (int i = 1; i < arr.length; i++) {
                cur.next = new Node(arr[i]);
                if (i == arr.length - 1) {
                    tail = cur.next;
                }
                cur = cur.next;
            }
        } else {
            head = null;
            tail = null;
        }
        size = arr.length;
    }

    private ImmutableLinkedList copy() {
        if (size == 0) {
            return new ImmutableLinkedList();
        }
        ImmutableLinkedList listCopy = new ImmutableLinkedList();
        listCopy.size = size;
        listCopy.head = new Node(head.data);
        if (size == 1) {
            listCopy.tail = listCopy.head;
        } else {
            Node cur = head.next;
            Node addTo = listCopy.head;
            while (cur != null) {
                addTo.next = new Node(cur.data);
                addTo = addTo.next;
                if (cur.next == null) {
                    listCopy.tail = addTo;
                }
                cur = cur.next;
            }
        }
        return listCopy;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur;
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        ImmutableLinkedList newList = copy();
        if (newList.head == null && index == 0) {
            newList.head = new Node(e);
            newList.tail = newList.head;
        } else if (index == 0) {
            newList.head = new Node(e, newList.head);
        } else if (index == newList.size) {
            newList.tail.next = new Node(e);
            newList.tail = newList.tail.next;
        } else {
            Node addTo = newList.getNode(index - 1);
            addTo.next = new Node(e, addTo.next);
        }
        newList.size += 1;
        return newList;
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        return add(size, e);
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        ImmutableLinkedList newList = copy();
        for (int i = 0; i < c.length; i++) {
            newList = newList.add(index + i, c[i]);
        }
        return newList;
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return addAll(size, c);
    }

    @Override
    public Object get(int index) {
        return getNode(index).data;
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        ImmutableLinkedList newList = copy();
        if (index == 0) {
            newList.head = newList.head.next;
        } else {
            Node removeFrom = newList.getNode(index - 1);
            removeFrom.next = removeFrom.next.next;
            if (index == newList.size - 1) {
                newList.tail = removeFrom;
            }
        }
        newList.size -= 1;
        return newList;
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        ImmutableLinkedList newList = copy();
        Node change = newList.getNode(index);
        change.data = e;
        return newList;
    }

    @Override
    public int indexOf(Object e) {
        Node cur = head;
        for (int i = 0; i < size; i++) {
            if (cur.data.equals(e)) {
                return i;
            }
            cur = cur.next;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        Node cur = head;
        for (int i = 0; i < size; i++) {
            arr[i] = cur.data;
            cur = cur.next;
        }
        return arr;
    }

    @Override
    public String toString() {
        String str = Arrays.toString(toArray());
        return str.substring(1, str.length() - 1);
    }

    public ImmutableLinkedList addFirst(Object e) {
        return add(0, e);
    }

    public ImmutableLinkedList addLast(Object e) {
        return add(e);
    }

    public Object getFirst() {
        return head.data;
    }

    public Object getLast() {
        return tail.data;
    }

    public ImmutableLinkedList removeFirst() {
        return remove(0);
    }

    public ImmutableLinkedList removeLast() {
        return remove(size - 1);
    }
}
