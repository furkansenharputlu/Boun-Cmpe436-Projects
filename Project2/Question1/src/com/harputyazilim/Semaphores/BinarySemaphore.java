// Furkan Şenharputlu // 2013400171 // furkan_senharputlu@hotmail.com
// CMPE436-Assignment 2

package com.harputyazilim.Semaphores;

//I got this code from course slides
public class BinarySemaphore { // used for mutual exclusion
    boolean value;

    public BinarySemaphore(boolean initValue) {
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
