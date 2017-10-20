// Furkan Şenharputlu // 2013400171 // furkan_senharputlu@hotmail.com
// CMPE436-Assignment 2

package com.harputyazilim;


public class Game {
    static int[][] grid;
    static int M, N, maxGenerations;
    CellThread[][] cellThreads;
    static Barrier barrier;


    public Game(int[][] grid, int maxGenerations) {
        this.grid = grid;
        this.maxGenerations = maxGenerations;
        M = grid.length;
        N = grid[0].length;
        this.cellThreads = new CellThread[M][N];
        generateThreads();
    }

    // This method plays the game
    public void play() {
        barrier = new Barrier();
        barrier.start();
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                cellThreads[i][j].start();
            }
        }

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                try {
                    cellThreads[i][j].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // This method generates all threads
    public void generateThreads() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                cellThreads[i][j] = new CellThread(i, j);
            }
        }
    }
}
