import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * It represents a farm with a given size and a list of positions
 */
public class Farm {
    // A variable that stores the size of the farm.
    public int Size;

    // A list of positions.
    public List<Position> Positions;

    // A constructor.
    public Farm(int size, List<Position> positions) {
        this.Size = size;
        this.Positions = positions;
    }

    /**
     * "Get all the positions that are of the given farm field type."
     * 
     * The first line of the function is the return type. In this case, it's a list
     * of positions
     * 
     * @param farmField The farm field you want to get the positions of.
     * @return A list of positions that are of the type of the farm field passed in.
     */
    public List<Position> getFieldPositions(FarmField farmField) {
        return this.Positions.stream().filter((p) -> p.FarmFieldType == farmField).collect(Collectors.toList());
    }

    /**
     * > If the position of the cow is not found in the list of positions, then
     * return null. Otherwise, set
     * the farm field type to cow
     * 
     * @param cowPosition The position of the cow.
     */
    public void placeCow(Position cowPosition) {
        this.Positions.stream().filter((p) -> p.IsExactMatch(cowPosition.XPos, cowPosition.YPos)).findAny()
                .orElse(null).FarmFieldType = FarmField.Cow;
    }

    /**
     * > For each cow, check if it has a haystack and a water pond as neighbours,
     * and if it does, add 2
     * to the score. Then, check if it has a haystack as neighbour, and if it does,
     * add 1 to the score.
     * Then, check if it has a cow as neighbour, and if it does, subtract 3 from the
     * score
     * 
     * @return The final score of the farm.
     */
    public int getFinalScore() {
        List<Position> cowPositions = this.getFieldPositions(FarmField.Cow);
        int finalScore = 0;

        for (int i = 0; i < cowPositions.size(); i++) {
            var cowPosition = cowPositions.get(i);

            int xPos = cowPosition.XPos;
            int yPos = cowPosition.YPos;
            int score = 0;

            List<Position> neighbourFields = new ArrayList<Position>();

            for (int x = Math.max(0, xPos - 1); x <= Math.min(xPos + 1, this.Size - 1); x++) {
                for (int y = Math.max(0, yPos - 1); y <= Math.min(yPos + 1, this.Size - 1); y++) {
                    if (x != xPos || y != yPos) {
                        final int xVar = x;
                        final int yVar = y;
                        neighbourFields
                                .add(this.Positions.stream().filter(p -> p.IsExactMatch(xVar, yVar)).findAny()
                                        .orElse(null));
                    }
                }
            }

            // It's checking if the cow has a haystack and a water pond as neighbours.
            if (neighbourFields.stream()
                    .filter((p) -> p.FarmFieldType == FarmField.HayStack && p.IsAnyMatch(xPos, yPos))
                    .findAny()
                    .orElse(null) != null
                    && neighbourFields.stream()
                            .filter((p) -> p.FarmFieldType == FarmField.WaterPond && p.IsAnyMatch(xPos, yPos))
                            .findAny()
                            .orElse(null) != null) {
                score += 2;
            }

            // It's checking if the cow has a haystack as neighbour.
            if (neighbourFields.stream()
                    .filter((p) -> p.FarmFieldType == FarmField.HayStack && p.IsAnyMatch(xPos, yPos))
                    .findAny()
                    .orElse(null) != null) {
                score += 1;
            }

            // It's checking if the cow has a cow as neighbour.
            if (neighbourFields.stream().filter((p) -> p.FarmFieldType == FarmField.Cow).findAny()
                    .orElse(null) != null) {
                score -= 3;
            }

            finalScore += score;
        }

        return finalScore;
    }

    /**
     * We create a new list of positions, and then we add a copy of each position in
     * the original list
     * to the new list.
     * 
     * @return A new Farm object with the same size and positions as the original
     *         Farm object.
     */
    public Farm clone() {
        List<Position> copyPositions = new ArrayList<>();
        for (Position pos : this.Positions) {
            copyPositions.add(pos.clone());
        }

        return new Farm(this.Size, copyPositions);
    }
}
