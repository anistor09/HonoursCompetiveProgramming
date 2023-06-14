
import java.io.File;

import java.util.*;

public class Main {


    public static void main(String[] args)  {
        // write your code here
        //Scanner rows = new Scanner(new File("src/com/company/datasets/hard/german-credit4.in"));
        try {

            System.out.print(mainCalculation(new Scanner(System.in))+"\n");
        } catch (Exception e) {
            System.out.println("error");
        }

//        System.out.println(expectedError == computedError);
//        System.out.println("computedError : " + computedError);
//        System.out.println("expectedError : " + expectedError);
//        long end = System.currentTimeMillis();
//        System.out.print("seconds " + ((double) (end - start) / 1000));

    }



    public static int mainCalculation(Scanner rows) {
        try {
           // rows.useDelimiter(System.lineSeparator());
            int depth = Integer.parseInt(rows.nextLine());

            //long start = System.currentTimeMillis();
            List<DataPoint> points = new ArrayList<>();
            int i = 0;

            while (rows.hasNext()) {
                String nextRow = rows.nextLine();

                String[] stringFeatures = nextRow.split(" ");
                int expected = Integer.parseInt(stringFeatures[0]);

                DataPoint d = new DataPoint(stringFeatures, i, expected);
                i++;

                points.add(d);
            }


            DecisionTree.nrPoints = points.size();
            DecisionTree.trees = new HashMap[ points.size()+1];
            for(int t = 0; t< DecisionTree.trees.length; t++ ){
                DecisionTree.trees[t] = new HashMap<Struct<TreeSet<Integer>,Integer>,Integer>();
            }


          return DecisionTree.computerError(points, new TreeSet<>(), depth, new boolean[points.get(0).features.length],
                    Integer.MAX_VALUE / 2);

        } catch (Exception e) {
            System.out.println("error");
        }
        return 0;
    }
}





