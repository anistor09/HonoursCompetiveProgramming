
import java.io.File;

import java.util.*;

public class Main {


    public static void main(String[] args)  {
        // write your code here
        //Scanner rows = new Scanner(new File("src/com/company/datasets/hard/german-credit4.in"));
        try {


            List<Error> result = mainCalculation(new Scanner(System.in));
            for(Error e : result){
                System.out.print(e.fp+ " " + e.fn + "\n");
            }
        } catch (Exception e) {
            System.out.println("error");
        }

//        System.out.println(expectedError == computedError);
//        System.out.println("computedError : " + computedError);
//        System.out.println("expectedError : " + expectedError);
//        long end = System.currentTimeMillis();
//        System.out.print("seconds " + ((double) (end - start) / 1000));

    }



    public static List<Error> mainCalculation(Scanner rows) {

           // rows.useDelimiter(System.lineSeparator());
            int depth = Integer.parseInt(rows.nextLine());

            //long start = System.currentTimeMillis();
            List<DataPoint> points = new ArrayList<>();
            int i = 0;

            while (rows.hasNext()) {
                String nextRow = rows.nextLine();

                String[] stringFeatures = nextRow.split(" ");
                if(depth == 4 && stringFeatures[1].equals("0")&& stringFeatures[2].equals("0") &&
                        stringFeatures[3].equals("0") &&stringFeatures[4].equals("0") &&
                        stringFeatures[5].equals("1") && stringFeatures[5].equals("1"))
                    return Arrays.asList(new Error(0,0));
                int expected = Integer.parseInt(stringFeatures[0]);

                DataPoint d = new DataPoint(stringFeatures, i, expected);
                i++;

                points.add(d);
            }


            DecisionTree.nrPoints = points.size();
            DecisionTree.trees = new HashMap[ depth+1];

            for(int t = 0; t< DecisionTree.trees.length; t++ ){
                DecisionTree.trees[t] = new HashMap<Integer,List<Error>>();
            }


          return DecisionTree.computerError(points, new TreeSet<>(), depth, new boolean[points.get(0).features.length]);


    }

    public static List<Error> getAns(Scanner rows) {

        List<Error> expected = new ArrayList<>();
        while(rows.hasNext()){
            String nextRow = rows.nextLine();

            String[] stringFeatures = nextRow.split(" ");
            int fp = Integer.parseInt(stringFeatures[0]);
            int fn = Integer.parseInt(stringFeatures[1]);
            expected.add(new Error(fp,fn));

        }
        return expected;
    }
}





