import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainTest {
    @Test
    public void easyTests() throws FileNotFoundException {
        File dir = new File("network reach");
        testFolder(dir);
    }
//    @Test
//    public void testFileTest() throws FileNotFoundException {
//        File dir = new File("watering flowers/");
//        testFolder(dir);
//    }
//    private testFile(){
//
//
//    }

    private static void testFolder(File dir) throws FileNotFoundException {
        File[] directoryListing = dir.listFiles();

        int testCount = 0, correct = 0;

        long start = System.currentTimeMillis();
        for(int i = 0; i < directoryListing.length; i += 2){
            File ans = directoryListing[i];
            System.out.println(ans.getName());
            File in = directoryListing[i + 1];
            Scanner scan = new Scanner(in);

            String expected = getAns(ans);
            String actual = Main.mainCalculation(scan);

            if(expected.equals(actual)){
                correct++;
            }else{
                System.out.println("expected:  " +expected + "actual "+ actual );
                System.out.println("error" + ans.getName());
            }
            testCount++;

            System.out.print("\033[2J");

        }
        long end = System.currentTimeMillis();

        System.out.println("\n------------------------\n");
        System.out.println("Passed: " + correct + "/" + testCount);
        //System.out.println("Total time: " + Helper.timeToString(start, end));
    }
    public static String getAns(File ansFile) throws FileNotFoundException {
        try {
            File in = ansFile;
            Scanner rows = new Scanner(in);
            String[] tokens = rows.nextLine().split(" ");


            return tokens[0] + " " + tokens[1];
        }catch(Exception e){
           return "no output file";
        }
    }
    //
//    @Test
//    public void mediumTests() throws FileNotFoundException {
//        File dir = new File("datasets/medium");
//        testFolder(dir);
//    }
//
//    @Test
//    public void hardTests() throws FileNotFoundException {
//        File dir = new File("datasets/hard");
//        testFolder(dir);
//    }
//
//    @Test
//    public void ownTests() throws FileNotFoundException {
//        File dir = new File("datasets/own");
//        testFolder(dir);
//    }
//    @Test
//    public void failed() throws FileNotFoundException {
//        String folder = "hard";
//        String name = "mushroom4";
//        File mainFile = new File("datasets/" + folder + "/" + name + ".in");
//        int answer = Main.getAns(new File("datasets/" + folder + "/" + name + ".ans"));
//        Scanner scan = new Scanner(mainFile);
//        int result = Main.mainCalculation(scan);
//        System.out.print("ans" + result);
//        System.out.print("exp" + answer);
//
//    }
//    @Test
//    public void testAverageOnAFile() throws FileNotFoundException {
//        String folder = "hard";
//        String name = "lymph4";
//
//        int numOfTests = 5;
//
//        File mainFile = new File("datasets/" + folder + "/" + name + ".in");
//        int answer = Main.getAns(new File("datasets/" + folder + "/" + name + ".ans"));
//        Scanner scan = new Scanner(mainFile);
//
//        int correct = 0;
//
//        double sum = 0.0;
//        for (int i = 0; i < numOfTests; i++) {
//            long start = System.currentTimeMillis();
//            int result = Main.mainCalculation(scan);
//            long end = System.currentTimeMillis();
//
//            if(result == answer){
//                correct++;
//            }
//          //  sum += Helper.getSeconds(start, end);
//        }
//
//        System.out.println("Average time is: " + sum / ((double) numOfTests) + " secs");
//        System.out.println("Correctly passed " + correct + "/" + numOfTests);
//    }

}