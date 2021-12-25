package dip107;

import java.awt.Point;
import java.util.Random;

public class SolveClass {
    private static Random rand = new Random();
    // For everyone to dive into
    /* TO DO: Create an algorithm which takes the path of zeros upon encountering the dead-end.
    *  When encountering a multiple paths, randomly choose the path that will be taken.
    *  Reset from (0, 0), if there is no more available paths to go (no zeros near, not end of the maze, dead-end encountered). */
    public static void RandomSolve(int[][] arrayToSolve) {
        int tries = 0;
        boolean mazeReseted = false;
        boolean insideMaze = true;
        boolean[] availableDirections = {false, false, false, false};
        boolean directionSelected = false;
        Point currentPos = new Point(0, 0);
        String path = "";

        // Creating an array for storing original values
        int[][] backupArray = new int[arrayToSolve.length][arrayToSolve[0].length];
        
        // Storing original values in backup array
        for (int i = 0; i < arrayToSolve.length; i++) {
            for (int j = 0; j < arrayToSolve[0].length; j++) {
                backupArray[i][j] = arrayToSolve[i][j];
            }
        }

        // Create a variable to store a path
        path = "(" + currentPos.x + "," + currentPos.y + ") ";

        // Go through loop while point is inside the maze
        while(insideMaze) {
            // Boolean variable to reset each time, when loop starts (set to true, when maze get reseted)
            mazeReseted = false;

            // Check if near cells can be zeros
            for(int i = 0; i < 4; i++) {
                switch(i) {
                    case 0: // Top element
                        if(currentPos.y != 0 && arrayToSolve[currentPos.y - 1][currentPos.x] == 0) {
                            availableDirections[0] = true;
                        }
                        break;
                    case 1: // Right element
                        if(currentPos.x != arrayToSolve[currentPos.y].length - 1 && arrayToSolve[currentPos.y][currentPos.x + 1] == 0) {
                            availableDirections[1] = true;
                        }
                        break;
                    case 2: // Bottom element
                        if(currentPos.y != arrayToSolve.length - 1 && arrayToSolve[currentPos.y + 1][currentPos.x] == 0) {
                            availableDirections[2] = true;
                        }
                        break;
                    case 3: // Left element
                        if(currentPos.x != 0 && arrayToSolve[currentPos.y][currentPos.x - 1] == 0) {
                            availableDirections[3] = true;
                        }
                        break;
                }
            }

            // If there is no paths to go, reset the position, reset the maze and increment the tries
            if ((availableDirections[0] == false && availableDirections[1] == false && availableDirections[2] == false && availableDirections[3] == false) &&
            tries <= arrayToSolve[0].length * arrayToSolve.length) {
                mazeReseted = true;
                currentPos.x = 0;
                currentPos.y = 0;
                path = "(" + currentPos.y + "," + currentPos.x + ") ";
                tries++;
                for (int i = 0; i < arrayToSolve.length; i++) {
                    for (int j = 0; j < arrayToSolve[0].length; j++) {
                        arrayToSolve[i][j] = backupArray[i][j];
                    }
                }
            }
            // If there is no paths to go and maze has been reseted many times (multiplication between width and height), exit the maze (no exit found)
            else if ((availableDirections[0] == false && availableDirections[1] == false && availableDirections[2] == false && availableDirections[3] == false) && 
            tries > arrayToSolve[0].length * arrayToSolve.length) {
                System.out.println("There is no exit");
                return;
            }

            // If maze got reseted, skip this block
            if (mazeReseted == false) {
                int direction;
                // Randomize and choose direction to move
                while (directionSelected != true) {
                    direction = rand.nextInt(4);
                    switch (direction) {
                        case 0: // Direction up
                            if(availableDirections[0] == true) {
                                arrayToSolve[currentPos.y][currentPos.x] = 1;
                                currentPos.y -= 1;
                                directionSelected = true;
                                path += "(" + currentPos.y + "," + currentPos.x + ") ";
                            }
                            break;
                        case 1: // Direction right
                            if(availableDirections[1] == true) {
                                arrayToSolve[currentPos.y][currentPos.x] = 1;
                                currentPos.x += 1;
                                directionSelected = true;
                                path += "(" + currentPos.y + "," + currentPos.x + ") ";                            
                            }
                            break;
                        case 2: // Direction down
                            if(availableDirections[2] == true) {
                                arrayToSolve[currentPos.y][currentPos.x] = 1;
                                currentPos.y += 1;
                                directionSelected = true;
                                path += "(" + currentPos.y + "," + currentPos.x + ") ";                            
                            }
                            break;
                        case 3: // Direction left
                            if(availableDirections[3] == true) {
                                arrayToSolve[currentPos.y][currentPos.x] = 1;
                                currentPos.x -= 1;
                                directionSelected = true;
                                path += "(" + currentPos.y + "," + currentPos.x + ") ";                            
                            }
                            break;
                    }
                }

                // Set values to false to continue without exceptions
                availableDirections[0] = false;
                availableDirections[1] = false;
                availableDirections[2] = false;
                availableDirections[3] = false;
                directionSelected = false;
            }

            // If point is equal to the end point, exit the cycle
            if(currentPos.y == arrayToSolve.length - 1 && currentPos.x == arrayToSolve[currentPos.y].length - 1) {
                insideMaze = false;
            }
        }
        System.out.println(path);
    }

    // For Aleksey and Daniil
    /* TO DO: Create an algorithm that takes the path of zeros upon encountering the dead-end. 
    *  When encountering a multiple paths, randomly choose the path that will be taken.
    *  Reset from (0, 0), if there is no more available paths to go.
    *  Use received information about failed paths and choose other path to go randomly. */
    public static void TryOutEverythingSolve(int[][] arrayToSolve) {
        int tries = 0;
        int pathsToGo = 0;
        boolean mazeReseted = false;
        boolean insideMaze = true;
        boolean[] availableDirections = {false, false, false, false};
        boolean directionSelected = false;
        Point currentPos = new Point(0, 0);
        Point intersectionExit = new Point(0, 0);
        String path = "";

        // Creating an array for storing original values
        int[][] backupArray = new int[arrayToSolve.length][arrayToSolve[0].length];
        
        // Storing original values in backup array
        for (int i = 0; i < arrayToSolve.length; i++) {
            for (int j = 0; j < arrayToSolve[0].length; j++) {
                backupArray[i][j] = arrayToSolve[i][j];
            }
        }

        // Create a variable to store a path
        path = "(" + currentPos.x + "," + currentPos.y + ") ";

        // Go through loop while point is inside the maze
        while(insideMaze) {
            // Boolean variable to reset each time, when loop starts (set to true, when maze get reseted)
            mazeReseted = false;

            // Check if near cells can be zeros
            for(int i = 0; i < 4; i++) {
                switch(i) {
                    case 0: // Top element
                        if(currentPos.y != 0 && arrayToSolve[currentPos.y - 1][currentPos.x] == 0) {
                            availableDirections[0] = true;
                            pathsToGo++;
                        }
                        break;
                    case 1: // Right element
                        if(currentPos.x != arrayToSolve[currentPos.y].length - 1 && arrayToSolve[currentPos.y][currentPos.x + 1] == 0) {
                            availableDirections[1] = true;
                            pathsToGo++;
                        }
                        break;
                    case 2: // Bottom element
                        if(currentPos.y != arrayToSolve.length - 1 && arrayToSolve[currentPos.y + 1][currentPos.x] == 0) {
                            availableDirections[2] = true;
                            pathsToGo++;
                        }
                        break;
                    case 3: // Left element
                        if(currentPos.x != 0 && arrayToSolve[currentPos.y][currentPos.x - 1] == 0) {
                            availableDirections[3] = true;
                            pathsToGo++;
                        }
                        break;
                }
            }

            // If there is no paths to go, reset the position, reset the maze and increment the tries (replace exit from the intersection with a wall, if there was one)
            if ((availableDirections[0] == false && availableDirections[1] == false && availableDirections[2] == false && availableDirections[3] == false) &&
            tries <= arrayToSolve[0].length * arrayToSolve.length) {
                mazeReseted = true;
                currentPos.x = 0;
                currentPos.y = 0;
                path = "(" + currentPos.y + "," + currentPos.x + ") ";
                tries++;
                pathsToGo = 0;

                if (intersectionExit.y != 0 && intersectionExit.x != 0)
                    backupArray[intersectionExit.y][intersectionExit.x] = 1;
                
                    for (int i = 0; i < arrayToSolve.length; i++) {
                    for (int j = 0; j < arrayToSolve[0].length; j++) {
                        arrayToSolve[i][j] = backupArray[i][j];
                    }
                }
            }
            // If there is no paths to go and maze has been reseted many times (multiplication between width and height), exit the maze (no exit found)
            else if ((availableDirections[0] == false && availableDirections[1] == false && availableDirections[2] == false && availableDirections[3] == false) && 
            tries > arrayToSolve[0].length * arrayToSolve.length) {
                System.out.println("There is no exit");
                return;
            }

            // If maze got reseted, skip this block
            if (mazeReseted == false) {
                int direction;
                // Randomize and choose direction to move
                while (directionSelected != true) {
                    direction = rand.nextInt(4);
                    switch (direction) {
                        case 0: // Direction up
                            if(availableDirections[0] == true) {
                                arrayToSolve[currentPos.y][currentPos.x] = 1;
                                currentPos.y -= 1;
                                directionSelected = true;
                                path += "(" + currentPos.y + "," + currentPos.x + ") ";

                                if(pathsToGo >= 2) {
                                    intersectionExit.x = currentPos.x;
                                    intersectionExit.y = currentPos.y;
                                }
                            }
                            break;
                        case 1: // Direction right
                            if(availableDirections[1] == true) {
                                arrayToSolve[currentPos.y][currentPos.x] = 1;
                                currentPos.x += 1;
                                directionSelected = true;
                                path += "(" + currentPos.y + "," + currentPos.x + ") "; 
                                
                                if(pathsToGo >= 2) {
                                    intersectionExit.x = currentPos.x;
                                    intersectionExit.y = currentPos.y;
                                }
                            }
                            break;
                        case 2: // Direction down
                            if(availableDirections[2] == true) {
                                arrayToSolve[currentPos.y][currentPos.x] = 1;
                                currentPos.y += 1;
                                directionSelected = true;
                                path += "(" + currentPos.y + "," + currentPos.x + ") "; 
                                
                                if(pathsToGo >= 2) {
                                    intersectionExit.x = currentPos.x;
                                    intersectionExit.y = currentPos.y;
                                }
                            }
                            break;
                        case 3: // Direction left
                            if(availableDirections[3] == true) {
                                arrayToSolve[currentPos.y][currentPos.x] = 1;
                                currentPos.x -= 1;
                                directionSelected = true;
                                path += "(" + currentPos.y + "," + currentPos.x + ") ";
                                
                                if(pathsToGo >= 2) {
                                    intersectionExit.x = currentPos.x;
                                    intersectionExit.y = currentPos.y;
                                }
                            }
                            break;
                    }
                }

                // Set values to false to continue without exceptions
                availableDirections[0] = false;
                availableDirections[1] = false;
                availableDirections[2] = false;
                availableDirections[3] = false;
                directionSelected = false;
                pathsToGo = 0;
            }

            // If point is equal to the end point, exit the cycle
            if(currentPos.y == arrayToSolve.length - 1 && currentPos.x == arrayToSolve[currentPos.y].length - 1) {
                insideMaze = false;
            }
        }
        System.out.println(path);
    }

    // For Alexander and Reinis
    /* TO DO: Create an algorithm that takes the path of zeros upon encountering the dead-end.
    *  When encountering a multiple paths, randomly choose the path that will be taken.
    *  If there is no more available paths to go, return to the previous intersection of multiple paths.
    *  Register the tried paths to use this information and choose other path. 
    */
    public static void RealLifeApproachSolve(int[][] arrayToSolve) {

    }
}
