import java.util.List;

/**
 * It's a class that represents a position on the grid
 */
public class Position {
    public int XPos;
    public int YPos;
    public FarmField FarmFieldType;

    // It's a constructor that takes in the x and y position of the cow and the type
    // of field it's on.
    public Position(int xPos, int yPos, char farmField) {
        this.XPos = xPos;
        this.YPos = yPos;

        switch (farmField) {
            case '.':
                this.FarmFieldType = FarmField.Grass;
                break;
            case '@':
                this.FarmFieldType = FarmField.HayStack;
                break;
            case '#':
                this.FarmFieldType = FarmField.WaterPond;
                break;
            default:
                this.FarmFieldType = FarmField.Cow;
                break;
        }
    }

    // It's a constructor that takes in the x and y position of the cow and the type
    // of field it's on.
    public Position(int xPos, int yPos, FarmField farmField) {
        this.XPos = xPos;
        this.YPos = yPos;
        this.FarmFieldType = farmField;
    }

    /**
     * If the X and Y positions are greater than or equal to 0 and less than the
     * grid size, then the
     * position is valid.
     * 
     * @param gridSize The size of the grid.
     * @return A boolean value.
     */
    public boolean IsValid(int gridSize) {
        return XPos > -1 && XPos < gridSize && YPos > -1 && YPos < gridSize;
    }

    /**
     * This function returns true if the x and y positions of the current object are
     * equal to the x and
     * y positions passed in as parameters.
     * 
     * @param xPos The x position of the mouse.
     * @param yPos The y position of the mouse.
     * @return A boolean value.
     */
    public boolean IsExactMatch(int xPos, int yPos) {
        return this.XPos == xPos && this.YPos == yPos;
    }

    /**
     * If the x position of the current object is equal to the x position passed in,
     * or if the y
     * position of the current object is equal to the y position passed in, return
     * true.
     * 
     * @param xPos The x position of the cell to check
     * @param yPos The y position of the tile to check.
     * @return A boolean value.
     */
    public boolean IsAnyMatch(int xPos, int yPos) {
        return this.XPos == xPos || this.YPos == yPos;
    }

    /**
     * Return true if any of the positions in the list match the current position.
     * 
     * @param positions A list of positions to check against.
     * @return A boolean value.
     */
    public boolean IsAnyPositionMatched(List<Position> positions) {
        return positions.stream().anyMatch((p) -> p.IsExactMatch(XPos, YPos));
    }

    /**
     * This function creates a new Position object with the same values as the
     * current Position object.
     * 
     * @return A new Position object with the same values as the original.
     */
    public Position clone() {
        return new Position(XPos, YPos, this.FarmFieldType);
    }
};

// It's an enum that represents the different types of fields on the farm.
enum FarmField {
    Grass("."),
    HayStack("@"),
    WaterPond("#"),
    Cow("C");

    private String value;

    FarmField(String val) {
        this.value = val;
    }

    public String toString() {
        return this.value;
    }
}