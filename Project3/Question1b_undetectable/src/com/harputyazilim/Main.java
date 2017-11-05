package com.harputyazilim;


import java.util.concurrent.TimeUnit;

public class Main {

    static int counter = 0;

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        t2.start();
        t1.start();


        try {
            t2.join();
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Happens-Before Based Detectors cannot detect this race condition");
    }
}
