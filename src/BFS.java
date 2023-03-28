import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * We start with a list of all the grass positions on the farm. We then iterate
 * through each position
 * and add it to a queue. We then iterate through the queue and perform the
 * transition function on each
 * position. If the transition function returns a farm with a score greater than
 * 0, we add all the
 * grass positions that are not in the current position to the queue
 */
public class BFS implements SearchAlgorithm {

    /**
     * > We start with a list of all the grass positions on the farm. We then
     * iterate through each
     * position and add it to a queue. We then iterate through the queue and perform
     * the transition
     * function on each position. If the transition function returns a farm with a
     * score greater than
     * 0, we add all the grass positions that are not in the current position to the
     * queue
     * 
     * @param farm The current state of the farm
     * @return A farm with the best possible score.
     */
    public Farm performSearch(Farm farm) {
        List<Position> grassPositions = farm.getFieldPositions(FarmField.Grass);

        // Creating a queue of lists of positions.
        Queue<List<Position>> frontier = new LinkedList<>();

        grassPositions.forEach((position) -> {
            var pos = new ArrayList<Position>();
            pos.add(position);
            frontier.add(pos);
        });

        // Iterating through the queue and performing the transition function on each
        // position. If the
        // transition function returns a farm with a score greater than 0, we add all
        // the grass
        // positions that are not in the current position to the queue
        while (!frontier.isEmpty()) {
            var p = frontier.remove();

            var sk = TransitionFunction(farm, p);

            // Checking if the current farm is a goal state. If it is, it returns the farm.
            if (goal(sk)) {
                return sk;
            }

            if (sk.getFinalScore() > 0) {
                var positions = grassPositions.stream().filter((position) -> !position.IsAnyPositionMatched(p))
                        .collect(Collectors.toList());
                positions.forEach((position) -> {
                    var pos = new ArrayList<Position>();
                    pos.addAll(p);
                    pos.add(position);
                    frontier.add(pos);
                });
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
