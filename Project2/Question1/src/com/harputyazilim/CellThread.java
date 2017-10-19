package com.harputyazilim;

import javafx.util.Pair;

import java.util.ArrayList;

public class CellThread extends Thread {
    private int i;
    private int j;

    public CellThread(int i, int j) {
        this.i = i;
        this.j = j;
    }


    @Override
    public void run() {
        Game.barrier.block(i,j);
        for(int i=0;i<Game.maxGenerations;i++){
            next();
    }
    }

    public void next()  {

        //condition 1 -> A 1 cell value stays 1 if exactly two or three neighbors are 1 valued.
        int next;
        int count = searchNeighbors(1);
        if (Game.grid[i][j] == 1) {
            if (count != 2 && count != 3) {
                next = 0;
            } else {
                next = 1;
            }
        } else {
            //condition 2 ->A 0 cell value goes to 1 if exactly three neighbors are 1 valued.
            if (count == 3) {
                next = 1;
            } else {
                next = 0;
            }
        }
        Game.barrier.block(i,j);
        Game.grid[i][j]=next;
        Game.barrier.block(i,j);

    }

    // This method returns the number of 1 or 0 neighbors of a cell
    private int searchNeighbors(int search) {
        ArrayList<Pair<Integer, Integer>> neighbors = new ArrayList<Pair<Integer, Integer>>();
        if (i > 0) {
            if (j > 0) {
                neighbors.add(new Pair<>(i - 1, j - 1));
            }
            neighbors.add(new Pair<>(i - 1, j));
            if (j < Game.N - 1) {
                neighbors.add(new Pair<>(i - 1, j + 1));
            }
        }
        if (j > 0) {
            neighbors.add(new Pair<>(i, j - 1));
        }
        if (j < Game.N - 1) {
            neighbors.add(new Pair<>(i, j + 1));
        }
        if (i < Game.M - 1) {
            if (j > 0) {
                neighbors.add(new Pair<>(i + 1, j - 1));
            }
            neighbors.add(new Pair<>(i + 1, j));
            if (j < Game.N - 1) {
                neighbors.add(new Pair<>(i + 1, j + 1));
            }
        }
        int temp = 0;
        for (int k = 0; k < neighbors.size(); k++) {
            int x = neighbors.get(k).getKey();
            int y = neighbors.get(k).getValue();
            temp += Game.grid[x][y] == search ? 1 : 0;
        }
        return temp;
    }
}
