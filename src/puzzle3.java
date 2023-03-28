import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * It reads the input file, performs the BFS, and writes the output file
 */
public class puzzle3 {
    public static void main(String[] args) {
        // Reading the input file and creating a Farm object.
        Farm farm = readFromInputFile(args[0]);

        // Calling the performBFS method of the BFS class and assigning the result to
        // the farm variable.

        // SearchAlgorithm searchAlgorithm = new BFS();

        SearchAlgorithm searchAlgorithm = new ID_DFS();
        farm = searchAlgorithm.performSearch(farm);

        // Writing the output to the file.
        writeToOutputFile(args[1], farm, farm.getFinalScore());
    }

    /**
     * It reads the input file and returns a Farm object
     * 
     * @param inputDir The path to the input file.
     * @return A Farm object
     */
    public static Farm readFromInputFile(String inputDir) {
        List<Position> farmPositions = new ArrayList<Position>();
        int gridSize = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputDir))) {
            String firstLine = bufferedReader.readLine();
            if (firstLine != null) {
                gridSize = Integer.parseInt(firstLine.trim());
            }

            int i = 0;
            String line = bufferedReader.readLine();
            while (i < gridSize && line != null) {
                for (int j = 0; j < gridSize; j++) {
                    farmPositions.add(new Position(i, j, line.charAt(j)));
                }

                line = bufferedReader.readLine();
                i++;
            }

        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }

        return new Farm(gridSize, farmPositions);
    }

    /**
     * It writes the farm's size, the farm's field types, and the final score to the
     * output file
     * 
     * @param outputDir  The path to the output file.
     * @param farm       The farm object that contains the final farm layout.
     * @param finalScore The final score of the farm.
     */
    public static void writeToOutputFile(String outputDir, Farm farm, int finalScore) {
        try {
            BufferedWriter f_writer = new BufferedWriter(new FileWriter(outputDir));
            f_writer.write(Integer.toString(farm.Size));
            f_writer.newLine();
            for (int i = 0; i < farm.Size; i++) {
                for (int j = 0; j < farm.Size; j++) {
                    final int iVar = i;
                    final int jVar = j;
                    f_writer.write(farm.Positions.stream().filter((p) -> p.IsExactMatch(iVar, jVar)).findAny()
                            .orElse(null).FarmFieldType.toString());
                    ;
                }

                f_writer.newLine();
            }

            f_writer.write(Integer.toString(finalScore));
            f_writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
