package dip107;

import java.util.Random;
import java.awt.Point;

public class LabyrinthClass {
    public static int[][] array;
    public static int rindas;
    public static int kolonnas;

    private static Random rand = new Random();
    public LabyrinthClass(int r, int k) {
        rindas = r;
        kolonnas = k;
        array = new int[r][k];
    }

    public static void GenerateLabyrinth(int number) {
        if(number == 1) {
            //Complete Random
            for(int i = 0; i < rindas; i++) {
                for(int j = 0; j < kolonnas; j++) {
                    array[i][j] = rand.nextInt(2);
                }
            }
        }
        else if (number == 2) {
            // Structured Random
            RandomFill(array);
        }
    }

    public static void RandomFill(int[][] arrayToFill) {
        boolean insideMaze = true;
        Point currentCell = new Point(0, 0);
        int numberOfAvailablePaths = 0;
        int direction = 0; //1 - left, 2 - up, 3- right, 4 - down
        while (insideMaze) {
            if(array[currentCell.x][currentCell.y] == 0) {
                // Situation, when point is near the border
                if(currentCell.x != 0 || currentCell.x != rindas-1 || currentCell.y != 0 || currentCell.y != kolonnas-1) {
                    numberOfAvailablePaths = 3;
                }
                // Situation, when point is near the border
                else {
                    numberOfAvailablePaths = 2;
                }

            }
            else if(currentCell.x == rindas-1 && currentCell.y == kolonnas-1) {
                insideMaze = false;
            }
        }
    }
}