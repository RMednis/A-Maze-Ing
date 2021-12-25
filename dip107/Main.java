package dip107;

import dip107.*;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int r = 0;
        int k = 0;

        System.out.print("row count: ");

        if (sc.hasNextInt()) {
            r = sc.nextInt();
        }
        else {
            System.out.println("input error");
            System.exit(1);
        }

        System.out.print("column count: ");

        if (sc.hasNextInt()) {
            k = sc.nextInt();
        }
        else {
            System.out.println("input error");
            System.exit(1);
        }

        char answer = 'x';

        sc.nextLine();

        System.out.print("Autofill the maze? (y/n) ");

        if(sc.hasNextLine()) {
            answer = sc.nextLine().charAt(0);
        }
        else {
            System.out.println("input error");
            System.exit(1);
        }

        LabyrinthClass labyrinth = new LabyrinthClass(r, k);

        if(answer == 'n') {
            for(int i = 0; i < r; i++) {
                for(int j = 0; j < k; j++) {
                    if(sc.hasNextInt()) {
                        LabyrinthClass.array[i][j] = sc.nextInt();   
                    }
                    else {
                        System.out.println("input error");
                        System.exit(1);
                    }
                }
            }
            // Setting up start and ending
            LabyrinthClass.array[0][0] = 0;
            LabyrinthClass.array[r-1][k-1] = 0;
        }

        else if (answer == 'y') {
            LabyrinthClass.GenerateLabyrinth(1);

            // Setting up start and ending
            LabyrinthClass.array[0][0] = 0;
            LabyrinthClass.array[r-1][k-1] = 0;

            for(int i = 0; i < r; i++) {
                for(int j = 0; j < k; j++) {
                    System.out.print(LabyrinthClass.array[i][j]);
                    if (j == k-1) {
                        System.out.print("\n");
                        continue;
                    }
                    System.out.print(" ");
                }
            }
        }

        int methodNum = 0;
        System.out.print("method number (1-3): ");

        if (sc.hasNextInt()) {
            methodNum = sc.nextInt();
        }
        else {
            System.out.println("input error");
            System.exit(1);
        }

        System.out.println("results:");

        switch(methodNum) {
            case 1:
                SolveClass.RandomSolve(LabyrinthClass.array);
                break;
            case 2:
                SolveClass.TryOutEverythingSolve(LabyrinthClass.array);
                break;
            case 3:

                break;
            default:
                System.out.println("input error");
                System.exit(1);
                break;
        }
    }
}
