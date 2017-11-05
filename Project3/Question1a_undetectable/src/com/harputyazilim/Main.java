package com.harputyazilim;


public class Main {

    static int counter = 0;

    public static void main(String[] args) {

        Lock l = new Lock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 1000; i++) {
                    try {
                        l.lock();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    counter++;
                    l.unlock();
                }

            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    try {
                        l.lock();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    counter++;
                    l.unlock();
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("Lockset algorithm cannot find a race because threads access a shared memory locating while holding a common lock");
        System.out.println(counter);
    }
}


class Lock {

    private boolean isLocked = false;

    public synchronized void lock()
            throws InterruptedException {
        while (isLocked) {
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
        notify();
    }
}
