// Furkan Şenharputlu // 2013400171 // furkan_senharputlu@hotmail.com
// CMPE436-Assignment 2

package com.harputyazilim.Semaphores;

//I got this code from course slides

public class CountingSemaphore { // Different from the book
    // used for synchronization of cooperating threads
    int value; // semaphore is initialized to the number of resources

    public CountingSemaphore(int initValue) {
        value = initValue;
    }

    public synchronized void P() { // blocking
        while (value == 0)
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        value--;
    }

    public synchronized void V() { // non-blocking
        value++;
        notify();
    }
}
