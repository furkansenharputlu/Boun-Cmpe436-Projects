// Furkan Şenharputlu // 2013400171 // furkan_senharputlu@hotmail.com
// CMPE436-Assignment 2

package com.harputyazilim;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int M = Integer.valueOf(args[0]);
        int N = Integer.valueOf(args[1]);
        int maxGenerations = Integer.valueOf(args[2]);
        int[][] grid;

        // According to input number, grid is declared
        if (args.length == 4) {
            grid = read(args[3], M, N);
        } else {
            grid = generateRandomGrid(M, N);
        }

        Game game = new Game(grid, maxGenerations);
        game.play();

        write("output.txt", Game.grid);
    }

    // This method reads the input from file
    public static int[][] read(String fileName, int M, int N) {
        Scanner scan = null;
        int[][] grid = null;
        try {
            scan = new Scanner(new File(fileName));
            grid = new int[M][N];
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    int v = scan.nextInt();
                    if (v != 0 && v != 1) {
                        // throws Exception when incorrect input is given
                        throw new IllegalArgumentException("Grid should contain 0 or 1 as value!");
                    }
                    grid[i][j] = v;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scan.close();
        }
        return grid;
    }

    // This method writes the output to file
    public static void write(String fileName, int[][] m) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            int r = m.length;
            int c = m[0].length;
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    if (j == c - 1) {
                        bw.write(String.valueOf(m[i][j]));
                    } else {
                        bw.write(m[i][j] + " ");
                    }
                }
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This method generates a random grid consisting of 1s and 0s
    private static int[][] generateRandomGrid(int M, int N) {
        int[][] randomGrid = new int[M][N];
        Random rand = new Random();
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (rand.nextBoolean()) {
                    randomGrid[i][j] = 1;
                } else {
                    randomGrid[i][j] = 0;
                }
            }
        }
        return randomGrid;
    }
}
