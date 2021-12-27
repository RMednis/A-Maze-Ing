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
            //Complete Random (when there is no logic)
            for(int i = 0; i < rindas; i++) {
                for(int j = 0; j < kolonnas; j++) {
                    array[i][j] = rand.nextInt(2);
                }
            }
        }
        else if (number == 2) {
            // Randomed Logic
            RandomFill(array);
        }
    }

    // For Daniil to implement
    public static void RandomFill(int[][] arrayToFill) {
        
        }
    }

