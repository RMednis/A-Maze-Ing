package dip107;

import java.awt.Point;
import java.lang.reflect.Array;
import java.util.*;

import dip107.LabyrinthClass;

public class SolveClass {
    private static final Random rand = new Random();

    // For everyone to dive into
    /* TODO: Create an algorithm which takes the path of zeros upon encountering the dead-end.
     *  When encountering a multiple paths, randomly choose the path that will be taken.
     *  Reset from (0, 0), if there is no more available paths to go (no zeros near, not end of the maze, dead-end encountered). */
    public void RandomSolve(LabyrinthClass Labyrinth) {

        int[][] arrayToSolve = Labyrinth.array;
        Labyrinth.path.clear();
        LinkedList<Point> path = Labyrinth.path;

        int tries = 0;
        boolean mazeReset = false;
        boolean insideMaze = true;
        boolean[] availableDirections = {false, false, false, false};
        boolean directionSelected = false;
        Point currentPos = new Point(0, 0);


        // Creating an array for storing original values
        int[][] backupArray = new int[arrayToSolve.length][arrayToSolve[0].length];

        // Storing original values in backup array
        for (int i = 0; i < arrayToSolve.length; i++) {
            System.arraycopy(arrayToSolve[i], 0, backupArray[i], 0, arrayToSolve[0].length);
        }

        // Store the first coord
        path.add((Point) currentPos.clone());;

        // Go through loop while point is inside the maze
        while (insideMaze) {
            // Boolean variable to reset each time, when loop starts (set to true, when maze get reseted)
            mazeReset = false;

            // Check if near cells can be zeros
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 0: // Top element
                        if (currentPos.y != 0 && arrayToSolve[currentPos.y - 1][currentPos.x] == 0) {
                            availableDirections[0] = true;
                        }
                        break;
                    case 1: // Right element
                        if (currentPos.x != arrayToSolve[currentPos.y].length - 1 && arrayToSolve[currentPos.y][currentPos.x + 1] == 0) {
                            availableDirections[1] = true;
                        }
                        break;
                    case 2: // Bottom element
                        if (currentPos.y != arrayToSolve.length - 1 && arrayToSolve[currentPos.y + 1][currentPos.x] == 0) {
                            availableDirections[2] = true;
                        }
                        break;
                    case 3: // Left element
                        if (currentPos.x != 0 && arrayToSolve[currentPos.y][currentPos.x - 1] == 0) {
                            availableDirections[3] = true;
                        }
                        break;
                }
            }

            boolean stuck = !availableDirections[0] && !availableDirections[1] && !availableDirections[2] && !availableDirections[3];
            // If there is no paths to go, reset the position, reset the maze and increment the tries
            if (stuck && tries <= arrayToSolve[0].length * arrayToSolve.length) {
                mazeReset = true;
                currentPos.setLocation(0,0);

                // Clear the path
                path.clear();
                path.add((Point) currentPos.clone());

                tries++;
                for (int i = 0; i < arrayToSolve.length; i++) {
                    System.arraycopy(backupArray[i], 0, arrayToSolve[i], 0, arrayToSolve[0].length);
                }
            }
            // If there is no paths to go and maze has been reseted many times (multiplication between width and height), exit the maze (no exit found)
            else if (stuck && tries > arrayToSolve[0].length) {
                Labyrinth.solved = false;
                return;
            }

            // If maze got reseted, skip this block
            if (!mazeReset) {
                int direction;
                // Randomize and choose direction to move
                while (!directionSelected) {
                    direction = rand.nextInt(4);
                    switch (direction) {
                        case 0: // Direction up
                            if (availableDirections[0]) {
                                arrayToSolve[currentPos.y][currentPos.x] = 1;
                                currentPos.y -= 1;
                                directionSelected = true;
                                path.add((Point) currentPos.clone());
                            }
                            break;
                        case 1: // Direction right
                            if (availableDirections[1]) {
                                arrayToSolve[currentPos.y][currentPos.x] = 1;
                                currentPos.x += 1;
                                directionSelected = true;
                                path.add((Point) currentPos.clone());
                            }
                            break;
                        case 2: // Direction down
                            if (availableDirections[2]) {
                                arrayToSolve[currentPos.y][currentPos.x] = 1;
                                currentPos.y += 1;
                                directionSelected = true;
                                path.add((Point) currentPos.clone());
                            }
                            break;
                        case 3: // Direction left
                            if (availableDirections[3]) {
                                arrayToSolve[currentPos.y][currentPos.x] = 1;
                                currentPos.x -= 1;
                                directionSelected = true;
                                path.add((Point) currentPos.clone());;
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
                path.clear();
            }

            // If point is equal to the end point, exit the cycle
            if (currentPos.y == arrayToSolve.length - 1 && currentPos.x == arrayToSolve[currentPos.y].length - 1) {
                insideMaze = false;
                Labyrinth.solved = true;
            }
        }
    }

    // For Aleksey and Daniil
    /* TODO: Create an algorithm that takes the path of zeros upon encountering the dead-end.
     *  When encountering a multiple paths, randomly choose the path that will be taken.
     *  Reset from (0, 0), if there is no more available paths to go.
     *  Use received information about failed paths and choose other path to go randomly. */
    public void TryOutEverythingSolve(LabyrinthClass Labyrinth) {

        int[][] arrayToSolve = Labyrinth.array;
        Labyrinth.path.clear();
        LinkedList<Point> path = Labyrinth.path;

        int tries = 0;
        int pathsToGo = 0;
        boolean mazeReseted = false;
        boolean insideMaze = true;
        boolean[] availableDirections = {false, false, false, false};
        boolean directionSelected = false;
        Point currentPos = new Point(0, 0);
        Point intersectionExit = new Point(0, 0);


        // Creating an array for storing original values
        int[][] backupArray = new int[arrayToSolve.length][arrayToSolve[0].length];

        // Storing original values in backup array
        for (int i = 0; i < arrayToSolve.length; i++) {
            for (int j = 0; j < arrayToSolve[0].length; j++) {
                backupArray[i][j] = arrayToSolve[i][j];
            }
        }

        // Store the first coord
        path.add((Point) currentPos.clone());

        // Go through loop while point is inside the maze
        while (insideMaze) {
            // Boolean variable to reset each time, when loop starts (set to true, when maze get reset)
            mazeReseted = false;
            int max_resets = arrayToSolve[0].length * arrayToSolve.length;


            // Check if near cells can be zeros
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 0: // Top element
                        if (currentPos.y != 0 && arrayToSolve[currentPos.y - 1][currentPos.x] == 0) {
                            availableDirections[0] = true;
                            pathsToGo++;
                        }
                        break;
                    case 1: // Right element
                        if (currentPos.x != arrayToSolve[currentPos.y].length - 1 && arrayToSolve[currentPos.y][currentPos.x + 1] == 0) {
                            availableDirections[1] = true;
                            pathsToGo++;
                        }
                        break;
                    case 2: // Bottom element
                        if (currentPos.y != arrayToSolve.length - 1 && arrayToSolve[currentPos.y + 1][currentPos.x] == 0) {
                            availableDirections[2] = true;
                            pathsToGo++;
                        }
                        break;
                    case 3: // Left element
                        if (currentPos.x != 0 && arrayToSolve[currentPos.y][currentPos.x - 1] == 0) {
                            availableDirections[3] = true;
                            pathsToGo++;
                        }
                        break;
                }
            }

            // We can't go anywhere...
            boolean stuck = !availableDirections[0] && !availableDirections[1] && !availableDirections[2] && !availableDirections[3];

            // If there is no paths to go, reset the position, reset the maze and increment the tries (replace exit from the intersection with a wall, if there was one)
            if (stuck && tries <= max_resets) {
                mazeReseted = true;
                currentPos.setLocation(0,0);

                // Clear the path
                path.clear();
                path.add((Point) currentPos.clone());

                tries++; // Increment tries
                pathsToGo = 0;

                if (intersectionExit.y != 0 && intersectionExit.x != 0)
                    backupArray[intersectionExit.y][intersectionExit.x] = 1;

                for (int i = 0; i < arrayToSolve.length; i++) {
                    System.arraycopy(backupArray[i], 0, arrayToSolve[i], 0, arrayToSolve[0].length);
                }
            }
            // If there is no paths to go and maze has been reset too many times (multiplication between width and height), exit the maze (no exit found)
            else if (stuck) {
                Labyrinth.solved = false;
                return;
            }

            // If the maze got reset, skip this block
            if (!mazeReseted) {
                int direction;
                // Randomize and choose direction to move
                while (!directionSelected) {
                    direction = rand.nextInt(4);
                    switch (direction) {
                        case 0: // Direction up
                            if (availableDirections[0]) {
                                arrayToSolve[currentPos.y][currentPos.x] = 1;
                                currentPos.y -= 1;
                                directionSelected = true;
                                path.add((Point) currentPos.clone());

                                if (pathsToGo >= 2) {
                                    intersectionExit.x = currentPos.x;
                                    intersectionExit.y = currentPos.y;
                                }
                            }
                            break;
                        case 1: // Direction right
                            if (availableDirections[1]) {
                                arrayToSolve[currentPos.y][currentPos.x] = 1;
                                currentPos.x += 1;
                                directionSelected = true;
                                path.add((Point) currentPos.clone());

                                if (pathsToGo >= 2) {
                                    intersectionExit.x = currentPos.x;
                                    intersectionExit.y = currentPos.y;
                                }
                            }
                            break;
                        case 2: // Direction down
                            if (availableDirections[2]) {
                                arrayToSolve[currentPos.y][currentPos.x] = 1;
                                currentPos.y += 1;
                                directionSelected = true;
                                path.add((Point) currentPos.clone());

                                if (pathsToGo >= 2) {
                                    intersectionExit.x = currentPos.x;
                                    intersectionExit.y = currentPos.y;
                                }
                            }
                            break;
                        case 3: // Direction left
                            if (availableDirections[3]) {
                                arrayToSolve[currentPos.y][currentPos.x] = 1;
                                currentPos.x -= 1;
                                directionSelected = true;
                                path.add((Point) currentPos.clone());

                                if (pathsToGo >= 2) {
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
            if (currentPos.y == arrayToSolve.length - 1 && currentPos.x == arrayToSolve[currentPos.y].length - 1) {
                insideMaze = false;
                Labyrinth.solved = true;
            }
        }
    }

    // For Alexander and Reinis
    /* TODO: Create an algorithm that takes the path of zeros upon encountering the dead-end.
     *  When encountering a multiple paths, randomly choose the path that will be taken.
     *  If there is no more available paths to go, return to the previous intersection of multiple paths.
     *  Register the tried paths to use this information and choose other path.
     */
    public void RealLifeApproachSolve(LabyrinthClass Labyrinth) {

        int[][] arrayToSolve = Labyrinth.array;
        LinkedList<Point> path = Labyrinth.path;

        Point current_pos = new Point(0, 0);
        LinkedList<Point> previous_intersections = new LinkedList<Point>(); // Used to store the intersection coords
        LinkedList<Integer> intersections_path = new LinkedList<Integer>(); // Used to store the last path element before the intersection

        while (!Labyrinth.solved) {

            boolean[] available_directions = {false, false, false, false};
            int directions_available_total = 0;

            // Check nearby cells to see where we can move to
            for (int direction_id = 0; direction_id < 4; direction_id++) {

                switch (direction_id) {
                    case 0: // Top element
                        if (current_pos.y != 0 && arrayToSolve[current_pos.y - 1][current_pos.x] == 0) {
                            available_directions[0] = true;
                            directions_available_total++;
                        }
                        break;

                    case 1: // Right element
                        if (current_pos.x != arrayToSolve[current_pos.y].length - 1 && arrayToSolve[current_pos.y][current_pos.x + 1] == 0) {
                            available_directions[1] = true;
                            directions_available_total++;
                        }
                        break;
                    case 2: // Bottom element
                        if (current_pos.y != arrayToSolve.length - 1 && arrayToSolve[current_pos.y + 1][current_pos.x] == 0) {
                            available_directions[2] = true;
                            directions_available_total++;
                        }
                        break;

                    case 3: // Left element
                        if (current_pos.x != 0 && arrayToSolve[current_pos.y][current_pos.x - 1] == 0) {
                            available_directions[3] = true;
                            directions_available_total++;
                        }
                        break;
                }
            }

            if (directions_available_total > 1) { // This is an intersection.... we should save it!
                if (!previous_intersections.isEmpty()) {
                    if (!(previous_intersections.getFirst() == current_pos)) {
                        previous_intersections.push((Point) current_pos.clone()); // Add it to the list!
                        intersections_path.push(path.size());
                    }
                } else {
                    previous_intersections.push((Point) current_pos.clone()); // Add it to the list!
                }
            }


            arrayToSolve[current_pos.y][current_pos.x] = -1; // Make sure we don't go back on ourselves!

            boolean stuck = !available_directions[0] && !available_directions[1] && !available_directions[2] && !available_directions[3];

            if (stuck) { // If we are in a dead end....
                if (!previous_intersections.isEmpty()) { // Make sure we still have intersections to go back to!

                    current_pos = previous_intersections.pop(); // Go back to the previous intersection


                    // Clear the current path until the last intersection!
                    Integer last_intersection_size = intersections_path.removeFirst();
                    while (path.size() > last_intersection_size) {
                        path.removeLast();
                    }

                } else {
                    System.out.println(Labyrinth.array.toString());
                    Labyrinth.solved = false; // Could not find an exit...
                    return;
                }
            }

            boolean direction_selected = false;
            while (!direction_selected) {
                if (stuck) break;
                int direction = rand.nextInt(4); // Randomly select a direction!
                switch (direction) {
                    case 0:
                        if (available_directions[0]) {
                            path.add((Point) current_pos.clone());
                            current_pos.y -= 1;
                            direction_selected = true;
                        }
                        break;
                    case 1:
                        if (available_directions[1]) {
                            path.add((Point) current_pos.clone());
                            current_pos.x += 1;
                            direction_selected = true;
                        }
                        break;
                    case 2:
                        if (available_directions[2]) {
                            path.add((Point) current_pos.clone());
                            current_pos.y += 1;
                            direction_selected = true;
                        }
                        break;
                    case 3:
                        if (available_directions[3]) {
                            path.add((Point) current_pos.clone());
                            current_pos.x -= 1;
                            direction_selected = true;
                        }
                        break;

                }
            }
            if ((current_pos.y == arrayToSolve.length - 1) && (current_pos.x == arrayToSolve[current_pos.y].length - 1)) {
                path.add((Point) current_pos.clone());
                Labyrinth.solved = true; // Found an exit!
            }
        }
    }


}

