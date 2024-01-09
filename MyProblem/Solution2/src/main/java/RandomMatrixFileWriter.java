import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class RandomMatrixFileWriter {

    /** Generator of input data for the Zombie avoidance problem that I propose for the Competitive Programming Honours
     *  Programme.**/
    public static void main(String[] args) {
        int numberOfMatrices = 5; // Number of matrices to generate

        int minRows = 1000;          // Minimum number of rows
        int maxRows = 2000;          // Maximum number of rows
        int minCols = 1000;          // Minimum number of columns
        int maxCols = 2000;          // Maximum number of columns

        File directory = new File("./datasets");
        if (!directory.exists()) {
            System.out.println(directory.mkdir());

        }

        Random random = new Random();

        for (int i = 0; i <= numberOfMatrices; i++) {
            int rows = random.nextInt(maxRows - minRows + 1) + minRows;
            int cols = random.nextInt(maxCols - minCols + 1) + minCols;

            int[][] matrix = generateRandomMatrix(rows, cols, 0.01);
            String filenameIn = "./datasets/large" + i + ".in";
            writeMatrixToFile(matrix, filenameIn);

            String filenameAns = "./datasets/large" + i + ".ans";
            Integer expResult = Main.solvePath(filenameIn);

            writeMatrixToFileAns(filenameAns, expResult);


        }
    }

    /** Write the expected answer to a file. **/

    private static void writeMatrixToFileAns(String filenameAns, Integer expResult) {
        try (FileWriter writer = new FileWriter(filenameAns)) {

            writer.write(expResult + "\n");

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * Generate a random 0/1 matrix using an uniform distribution.
     */
    public static int[][] generateRandomMatrix(int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(2);  // Generates either 0 or 1
            }
        }
        matrix[0][0] = 1;
        matrix[rows - 1][cols - 1] = 0;

        return matrix;
    }

    /**
     * Generate a random 0/1 matrix using a lower probability for the 1 values.
     */
    public static int[][] generateRandomMatrix(int rows, int cols, double probability) {
        int[][] matrix = new int[rows][cols];
        Random random = new Random();

        // Calculate the number of elements that should be 1
        int totalElements = rows * cols;
        int numberOfOnes = (int) (probability * totalElements);  // 30% of total elements

        // Assign 1s to approximately 30% of the matrix
        for (int i = 0; i < numberOfOnes; i++) {
            int rowIndex, colIndex;
            do {
                rowIndex = random.nextInt(rows);
                colIndex = random.nextInt(cols);
            } while (matrix[rowIndex][colIndex] == 1);  // Ensure the position is not already set to 1
            matrix[rowIndex][colIndex] = 1;
        }

        matrix[0][0] = 0;
        matrix[rows - 1][cols - 1] = 0;

        return matrix;
    }

    /**
     * * Write the give matrix to a file.
     */
    public static void writeMatrixToFile(int[][] matrix, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {

            writer.write(matrix.length + "\n" + matrix[0].length + "\n");

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    writer.write(matrix[i][j] + " ");
                }
                writer.write("\n");  // New line after each row
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
