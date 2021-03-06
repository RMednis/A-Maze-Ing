package dip107;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;


public class LabyrinthClass {
    public int[][] array;
    public int rindas;
    public int kolonnas;
    public boolean solved = false;
    public LinkedList<Point> path = new LinkedList<>();

    private static final Random rand = new Random();

    public LabyrinthClass(int r, int k) {
        rindas = r;
        kolonnas = k;
        array = new int[r][k];
        solved = false;
    }

    public void GenerateLabyrinth(int number) {
        switch (number) {
            case 1: {
                //Complete Random
                for (int i = 0; i < rindas; i++) {
                    for (int j = 0; j < kolonnas; j++) {
                        array[i][j] = rand.nextInt(2);
                    }
                }
            }
            case 2: {
                // Structured Random
                RandomFill(array);
            }
        }
    }

    public void RandomFill(int[][] arrayToFill) {
        // Structured Random fill
        int r = rindas;
        int k = kolonnas;
        int i = 0;
        int j = 0;

        k = k - 1;
        int border1 = k;

        r = r - 1;
        int border2 = r;

        for (i = 0; i <= border1; i++)
            for (j = 0; j <= border2; j++)
                arrayToFill[j][i] = 1;
        i = 0;
        j = 0;
        arrayToFill[j][i] = 0;
        arrayToFill[r][k] = 0;
        do {
            int dir = rand.nextInt(4);

            switch (dir) {
                case 1:
                    //forward
                    if (i < border1) {
                        arrayToFill[j][i + 1] = 0;
                        i++;
                    }
                    break;
                case 2:
                    //right
                    if (j < border2) {
                        arrayToFill[j + 1][i] = 0;
                        j++;
                    }
                    break;
                case 3:
                    //backward
                    if (i > 0) {
                        arrayToFill[j][i - 1] = 0;
                        i--;
                    }
                    break;
                case 0:
                    //left
                    if (j > 0) {
                        arrayToFill[j - 1][i] = 0;
                        j--;
                    }
                    break;
            }
            if (i == k && j == r)
                break;
        }
        while (true);
    }
}
