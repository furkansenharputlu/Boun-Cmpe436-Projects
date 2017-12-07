package com.harputyazilim.todos;

public class MySemaphore {
    boolean value;

    public MySemaphore(boolean initValue) {
        value = initValue;
    }

    public synchronized void P() { // atomic operation // blocking
        while (value == false)
            try {
                wait(); // add process to the queue of blocked processes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        value = false;
    }

    public synchronized void V() { // atomic operation // non-blocking
        value = true;
        notify(); // wake up a process from the queue
    }
}
