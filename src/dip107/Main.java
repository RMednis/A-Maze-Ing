package dip107;

import java.awt.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int r;
        int k;

        Scanner sc = new Scanner(System.in);
        System.out.print("row count: ");
        r = numberCheck(sc);

        System.out.print("column count: ");
        k = numberCheck(sc);

        sc.nextLine();
        System.out.print("Autofill the maze? (y/n) ");

        LabyrinthClass labyrinth = new LabyrinthClass(r, k);
        SolveClass solver = new SolveClass();

        if (!yesNoCheck(sc)) { // Manual entry
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < k; j++) {
                    labyrinth.array[i][j] = numberCheck(sc);
                }
            }
            // Setting up start and ending
            labyrinth.array[0][0] = 0;
            labyrinth.array[r - 1][k - 1] = 0;
        } else { // Automatic maze generation
            System.out.print("Maze generation method (1-2): ");

            switch (numberCheck(sc)) {
                case 1 -> labyrinth.GenerateLabyrinth(1);
                case 2 -> labyrinth.GenerateLabyrinth(2);
                default -> inputError();
            }

            // Setting up start and ending
            labyrinth.array[0][0] = 0;
            labyrinth.array[r - 1][k - 1] = 0;


            System.out.print("Pretty print the maze? (y/n) ");

            boolean pretty = yesNoCheck(sc);
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < k; j++) {

                    if (pretty) { // Pretty print the maze using block characters
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

            switch (numberCheck(sc)) {
                case 1 -> solver.RandomSolve(labyrinth);
                case 2 -> solver.TryOutEverythingSolve(labyrinth);
                case 3 -> solver.RealLifeApproachSolve(labyrinth);
                default -> inputError();
            }

            System.out.println("results:");


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

    private static boolean yesNoCheck(Scanner sc) {
        if (sc.hasNextLine()) {
            char input = sc.next().charAt(0);
            switch (input) {
                case 'y': return true;
                case 'n': return false;
                default: {
                    inputError();
                    return false;
                }
            }
        } else {
            inputError();
            return false;
        }
    }

    private static int numberCheck(Scanner sc) {
        if (sc.hasNextInt()) {
            return sc.nextInt();
        } else {
            inputError();
            return 0;
        }
    }

    private static void inputError() {
        System.out.println("input error");
        System.exit(1);
    }
}
