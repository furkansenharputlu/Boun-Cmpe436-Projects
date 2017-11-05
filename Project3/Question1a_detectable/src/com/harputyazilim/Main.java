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

        System.out.println("Threads do not use a common lock so algorithm can find the race condition");
    }
}
