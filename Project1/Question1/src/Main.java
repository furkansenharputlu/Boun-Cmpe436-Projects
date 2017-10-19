// FURKAN ŞENHARPUTLU // 2013400171 // furkan_senharputlu@hotmail.com
// CMPE436-Assignment 1

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int[][] m1 = read(args[0]);
        int[][] m2 = read(args[1]);
        int[][] res = multiply(m1, m2);
        write(args[2], res);

    }

    //This methdd returns the multiplication of matrices
    public static int[][] multiply(int[][] m1, int[][] m2) {
        int r1 = m1.length;
        int c1 = m1[0].length;
        int c2 = m2[0].length;
        int[][] res = new int[r1][c2];

        for (int i = 0; i < r1; i++) { // m1 Row
            for (int j = 0; j < c2; j++) { // m2 Column
                for (int k = 0; k < c1; k++) { // m1 Column
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }

    // This method reads the input from file
    public static int[][] read(String fileName) {
        Scanner scan = null;
        int[][] m = null;
        try {
            scan = new Scanner(new File(fileName));
            int r = scan.nextInt();
            int c = scan.nextInt();
            m = new int[r][c];
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    m[i][j] = scan.nextInt();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scan.close();
        }
        return m;
    }

    // This method writes the output to file
    public static void write(String fileName, int[][] m) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            int r = m.length;
            int c = m[0].length;
            bw.write(r + " " + c);
            bw.newLine();
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
}



