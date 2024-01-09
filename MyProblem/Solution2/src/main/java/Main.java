import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    /** Get the result by reading from the console **/
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Integer res = getMaxSafenessScore(sc);
        System.out.println(res);

    }
    /** Get the result by reading from the  file **/
    public static int solvePath(String filePath) {
        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return getMaxSafenessScore(scanner);
    }

    /** Reading the input **/
    public static int getMaxSafenessScore(Scanner sc) {

        int n = Integer.parseInt(sc.nextLine());
        int m = Integer.parseInt(sc.nextLine());
        List<List<Integer>> input = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            List<Integer> current = new ArrayList<>();
            Scanner line = new Scanner(sc.nextLine());

            for (int j = 0; j < m; j++) {
                Integer next = line.nextInt();
                current.add(next);
            }
            input.add(current);

        }
        Solution s = new Solution();
        return s.maximumSafenessScore(input);
    }


}
