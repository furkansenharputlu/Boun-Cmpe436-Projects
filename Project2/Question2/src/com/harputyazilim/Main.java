// Furkan Şenharputlu // 2013400171 // furkan_senharputlu@hotmail.com
// CMPE436-Assignment 2

package com.harputyazilim;

public class Main {

    public static void main(String[] args) {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Thread t1 = new Thread(new Runner(obj1, obj2));
        Thread t2 = new Thread(new Runner(obj2, obj3));
        Thread t3 = new Thread(new Runner(obj3, obj1));
        t1.start();
        t2.start();
        t3.start();
    }
}

// This an example thread
class Runner implements Runnable {
    private Object obj1;
    private Object obj2;

    public Runner(Object o1, Object o2) {
        this.obj1 = o1;
        this.obj2 = o2;
    }

    @Override
    public void run() {
        synchronized (obj1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("This will be printed!");
            synchronized (obj2) { // All will wait for another to relase an object
                System.out.println("This will not be printed because of Deadlock!");
            }
        }
    }
}
