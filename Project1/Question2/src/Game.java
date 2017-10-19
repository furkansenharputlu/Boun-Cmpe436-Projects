// FURKAN ŞENHARPUTLU // 2013400171 // furkan_senharputlu@hotmail.com
// CMPE436-Assignment 1

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    int[][] grid; // initial state of the grid
    int[][] nextGeneration; // next generated state of the grid
    int M, N, maxGenerations;


    public Game(int[][] grid, int maxGenerations) {
        this.grid = grid;
        this.maxGenerations = maxGenerations;
        M = grid.length;
        N = grid[0].length;
        this.nextGeneration = new int[M][N];
    }

    // This method plays the game
    public void play() {
        for (int i = 0; i < maxGenerations; i++) {
            next();
            grid = new int[M][N];
            for (int k = 0; k < grid.length; k++)
                grid[k] = Arrays.copyOf(nextGeneration[k], nextGeneration[k].length);
        }
    }

    // This method makes the next generation according to given rules
    public void next() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                //condition 1 -> A 1 cell value stays 1 if exactly two or three neighbors are 1 valued.
                int count = searchNeighbors(i, j, 1);
                if (grid[i][j] == 1) {
                    if (count != 2 && count != 3) {
                        nextGeneration[i][j] = 0;
                    } else {
                        nextGeneration[i][j] = 1;
                    }
                } else {
                    //condition 2 ->A 0 cell value goes to 1 if exactly three neighbors are 1 valued.
                    if (count == 3) {
                        nextGeneration[i][j] = 1;
                    } else {
                        nextGeneration[i][j] = 0;
                    }
                }
            }
        }
    }

    // This method returns the number of 1 or 0 neighbors of a cell
    private int searchNeighbors(int i, int j, int search) {
        ArrayList<Pair<Integer, Integer>> neighbors = new ArrayList<Pair<Integer, Integer>>();
        if (i > 0) {
            if (j > 0) {
                neighbors.add(new Pair<>(i - 1, j - 1));
            }
            neighbors.add(new Pair<>(i - 1, j));
            if (j < N - 1) {
                neighbors.add(new Pair<>(i - 1, j + 1));
            }
        }
        if (j > 0) {
            neighbors.add(new Pair<>(i, j - 1));
        }
        if (j < N - 1) {
            neighbors.add(new Pair<>(i, j + 1));
        }
        if (i < M - 1) {
            if (j > 0) {
                neighbors.add(new Pair<>(i + 1, j - 1));
            }
            neighbors.add(new Pair<>(i + 1, j));
            if (j < N - 1) {
                neighbors.add(new Pair<>(i + 1, j + 1));
            }
        }
        int temp = 0;
        for (int k = 0; k < neighbors.size(); k++) {
            int x = neighbors.get(k).getKey();
            int y = neighbors.get(k).getValue();
            temp += grid[x][y] == search ? 1 : 0;
        }
        return temp;
    }
}

