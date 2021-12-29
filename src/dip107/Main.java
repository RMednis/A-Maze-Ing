package dip107;

import java.awt.*;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int r = 0;
        int k = 0;

        System.out.print("row count: ");

        if (sc.hasNextInt()) {
            r = sc.nextInt();
        } else {
            System.out.println("input error");
            System.exit(1);
        }

        System.out.print("column count: ");

        if (sc.hasNextInt()) {
            k = sc.nextInt();
        } else {
            System.out.println("input error");
            System.exit(1);
        }

        char answer = 'x';

        sc.nextLine();

        System.out.print("Autofill the maze? (y/n) ");

        if (sc.hasNextLine()) {
            answer = sc.nextLine().charAt(0);
        } else {
            System.out.println("input error");
            System.exit(1);
        }

        LabyrinthClass labyrinth = new LabyrinthClass(r, k);
        SolveClass solver = new SolveClass();

        if (answer == 'n') {
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < k; j++) {
                    if (sc.hasNextInt()) {
                        labyrinth.array[i][j] = sc.nextInt();
                    } else {
                        System.out.println("input error");
                        System.exit(1);
                    }
                }
            }
            // Setting up start and ending
            labyrinth.array[0][0] = 0;
            labyrinth.array[r - 1][k - 1] = 0;
        } else if (answer == 'y') {
            int methodNum = 0;
            System.out.print("Maze generation method (1-2): ");

            if (sc.hasNextInt()) {
                methodNum = sc.nextInt();
            } else {
                System.out.println("input error");
                System.exit(1);
            }

            switch (methodNum) {
                case 1 -> labyrinth.GenerateLabyrinth(1);
                case 2 -> labyrinth.GenerateLabyrinth(2);
                default -> {
                    System.out.println("input error");
                    System.exit(1);
                }
            }

            // Setting up start and ending
            labyrinth.array[0][0] = 0;
            labyrinth.array[r - 1][k - 1] = 0;


            System.out.print("Pretty print the maze? (y/n)");

            if (sc.hasNextLine()) {
                answer = sc.next().charAt(0);
            } else {
                System.out.println("input error");
                System.exit(1);
            }


            for (int i = 0; i < r; i++) {
                for (int j = 0; j < k; j++) {

                    if (answer == 'y') { // Pretty print the maze using block characters
                        if (labyrinth.array[i][j] == 1) {
                            System.out.print("▉");
                        } else {
                            System.out.print("░");
                        }
                    } else { // Print the maze out using the raw values
                        System.out.print(labyrinth.array[i][j]);
                    }

                    if (j == k - 1) {
                        System.out.print("\n");
                        continue;
                    }
                    System.out.print(" ");
                }
            }

            System.out.print("method number (1-3): ");

            if (sc.hasNextInt()) {
                methodNum = sc.nextInt();
            } else {
                System.out.println("input error");
                System.exit(1);
            }

            System.out.println("results:");

            switch (methodNum) {
                case 1 -> solver.RandomSolve(labyrinth);
                case 2 -> solver.TryOutEverythingSolve(labyrinth);
                case 3 -> solver.RealLifeApproachSolve(labyrinth);
                default -> {
                    System.out.println("input error");
                    System.exit(1);
                }
            }

            // Printing the maze once it's been solved (or not)
            if (labyrinth.solved) {

                StringBuilder points_formatted = new StringBuilder();

                // Convert all the points in the Linked List to a string of coords
                for (int point_id = 0; point_id < labyrinth.path.size(); point_id++) {
                    Point point = labyrinth.path.get(point_id);

                    // Format the points, add them to the big string!
                    String current_point = String.format("(%d,%d) ",point.x, point.y);
                    points_formatted.append(current_point);
                }

                System.out.println(points_formatted); // Print everything
            } else {
                // Welp... we couldn't solve the maze...
                System.out.println("Couldn't find an exit... :( ");
            }


        }
    }
}
