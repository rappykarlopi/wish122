package com.example.wish122;

public class OurCircularQueue<T> {

    int currSize, front, back, maxSize;
    T[] arr;

    public OurCircularQueue(int size) {
        arr = (T[]) new Object[size];
        this.maxSize = size;
        this.currSize = 0;
        this.front = 0;
        this.back = -1;
    }

    public void enqueue(Object item) {
        if (full()) {
            throw new IllegalStateException("Queue is full.");
        }
        back = (back + 1)%maxSize;
        arr[back] = (T) item;
        currSize++;
    }
    public Object dequeue() {
        if (empty()) {
            throw new IllegalStateException("Queue is empty.");
        }
        T item = arr[front];
        front = (front + 1) % maxSize;
        currSize--;
        return item;
    }
    public Object front() {
        if (empty()) {
            throw new IllegalStateException("Queue is empty.");
        }
        return arr[front];
    }

    public Object back() {
        if (empty()) {
            throw new IllegalStateException("Queue is empty.");
        }
        return arr[back];
    }
    public int size() {
        return currSize;
    }

    public boolean empty() {
        return currSize == 0;
    }

    public boolean full() {
        return currSize == maxSize;
    }
}