import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This class implements the SearchAlgorithm interface and performs a
 * depth-first search on a given
 * farm.
 */
public class ID_DFS implements SearchAlgorithm {

    // A list of all the grass positions in the farm.
    List<Position> grassPositions;

    /**
     * This function takes a farm and returns a farm.
     * 
     * @param farm The farm object that is being searched.
     * @return A Farm object.
     */
    public Farm performSearch(Farm farm) {
        int depth = 1;
        grassPositions = farm.getFieldPositions(FarmField.Grass);

        var cowsPlacedFarm = farm.clone();

        while (!goal(cowsPlacedFarm)) {
            cowsPlacedFarm = performIDDFS(farm, depth);
            depth = depth + 1;
        }

        return cowsPlacedFarm;
    }

    public Farm performIDDFS(Farm farm, int depth) {

        // Creating a queue of lists of positions.
        Stack<List<Position>> frontier = new Stack<List<Position>>();

        for (int i = grassPositions.size() - 1; i >= 0; i--) {
            var position = grassPositions.get(i);
            var pos = new ArrayList<Position>();
            pos.add(position);
            frontier.push(pos);
        }

        // Iterating through the stack and performing the transition function on each
        // position. If the
        // transition function returns a farm with a score greater than 0, we add all
        // the grass
        // positions that are not in the current position to the stack
        while (!frontier.isEmpty()) {
            var p = frontier.pop();

            if (p.size() == depth) {

                var sk = TransitionFunction(farm, p);

                // Checking if the current farm is a goal state. If it is, it returns the farm.
                if (goal(sk)) {
                    return sk;
                }

                if (depth == 1 && sk.getFinalScore() == 0) {
                    grassPositions = grassPositions.stream().filter((position) -> !position.IsAnyPositionMatched(p))
                            .toList();
                }
            }

            var positions = grassPositions.stream().filter((position) -> !position.IsAnyPositionMatched(p))
                    .toList();
            for (int i = positions.size() - 1; i >= 0; i--) {
                var position = positions.get(i);
                var pos = new ArrayList<Position>();
                pos.addAll(p);
                pos.add(position);
                if (pos.size() <= depth) {
                    frontier.push(pos);
                }
            }
        }

        return farm;
    }

    /**
     * Given a farm and a list of cow placements, return a new farm with the cows
     * placed.
     * 
     * @param farm          The current state of the farm.
     * @param cowPlacements A list of positions where cows are placed.
     * @return A new farm with the cows placed.
     */
    public Farm TransitionFunction(Farm farm, List<Position> cowPlacements) {
        Farm copyFarm = farm.clone();

        for (Position cowPosition : cowPlacements) {
            copyFarm.placeCow(cowPosition);
        }

        return copyFarm;
    }

    /**
     * If the final score is greater than or equal to 7, return true, otherwise
     * return false.
     * 
     * @param farm The farm object that contains all the information about the farm.
     * @return The goal method is returning a boolean value.
     */
    public boolean goal(Farm farm) {
        // Checking if the final score is greater than or equal to 7.
        if (farm.getFinalScore() >= 7) {
            return true;
        }

        return false;
    }
}
