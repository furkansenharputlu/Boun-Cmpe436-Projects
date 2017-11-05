package com.harputyazilim;

public class Main {

    static int counter = 0;

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    counter++;
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    counter++;
                }
            }
        });

        t1.start();
        t2.start();

        System.out.println("Happens-Before Based Detectors can detect this race condition");
    }
}
