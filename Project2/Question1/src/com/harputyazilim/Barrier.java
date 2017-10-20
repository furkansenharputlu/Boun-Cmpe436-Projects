// Furkan Şenharputlu // 2013400171 // furkan_senharputlu@hotmail.com
// CMPE436-Assignment 2

package com.harputyazilim;

import com.harputyazilim.Semaphores.BinarySemaphore;
import com.harputyazilim.Semaphores.CountingSemaphore;

public class Barrier extends Thread {
    int M = Game.M;
    int N = Game.N;

    private CountingSemaphore arrive = null;
    private BinarySemaphore[][] release = null;

    public Barrier() {
        arrive = new CountingSemaphore(0);
        release = new BinarySemaphore[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                release[i][j] = new BinarySemaphore(false);
            }
        }
    }

    // This block the coming thread until others come
    public void block(int i, int j) {
        arrive.V(); // signal for arrival
        release[i][j].P(); // wait for release
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < M * N; i++)
                arrive.P(); // wait for all arrivals


            for (int i = 0; i < M; i++)
                for (int j = 0; j < N; j++) {
                    release[i][j].V(); // signal for all releases
                }
        }
    }
}
