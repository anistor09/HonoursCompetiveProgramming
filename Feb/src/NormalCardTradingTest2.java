import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class NormalCardTradingTest2 {

    @Test
    void solve() {
    }

    @Test
    void solveFair() throws FileNotFoundException {
        File dir = new File("src/fair");
        testFolder(dir);
    }
    @Test
    void solveFairExtra() throws FileNotFoundException {
        File dir = new File("src/extra instances/fair");
        testFolder(dir);
    }
    @Test
    void solveFairExtraString() throws FileNotFoundException {
        File dir = new File("src/fair");
        testFolderString(dir);
    }
    private  void testFolder(File dir) throws FileNotFoundException {
        File[] directoryListing = dir.listFiles();

        int testCount = 0, correct = 0;


        for(int i = 1; i < directoryListing.length; i += 3){
            File ans = directoryListing[i];
            System.out.println(ans.getName());
            File in = directoryListing[i + 1];
            System.out.println(in.getName());
            Scanner scan = new Scanner(in);

            int[] expected = getAns(ans);
            NormalCardTrading nc = new NormalCardTrading();
            int first =nc.solve(scan);
            int second = nc.solveFair();

            if(expected[0] == first && expected[1] == second){
                correct++;
            }
            testCount++;

            System.out.print("\033[2J");
            System.out.println(testCount + "/40");
        }


        System.out.println("\n------------------------\n");
        System.out.println("Passed: " + correct + "/" + testCount);

    }

    private  void testFolderString(File dir) throws FileNotFoundException {
        File[] directoryListing = dir.listFiles();

        int testCount = 0, correct = 0;


        for (int i = 0; i < directoryListing.length; i += 3) {
            File ans = directoryListing[i];
            System.out.println(ans.getName());
            File in = directoryListing[i + 1];
            System.out.println(in.getName());
            Scanner scan = new Scanner(in);

            String expected = getAnsString(ans);
            NormalCardTrading nc = new NormalCardTrading();
            int first = nc.solve(scan);
            int second = nc.solveFair();

            if (expected.equals(first + " " + second)) {
//                System.out.print(expected);
//                System.out.print(first + " " + second);
//                System.out.print("###");
                correct++;
            }else{
                System.out.print("wrong");
                System.out.println(expected);
                System.out.print(first + " " + second);

            }

            testCount++;

            System.out.print("\033[2J");
            System.out.println(testCount + "/40");
        }


        System.out.println("\n------------------------\n");
        System.out.println("Passed: " + correct + "/" + testCount);

    }
        public  int[] getAns(File file)  {

        int[] expected = new int[2] ;
        try{
            Scanner sc  = new Scanner(file);
            String[] firstLine = sc.nextLine().split(" ");
            expected[0] = Integer.parseInt(firstLine[0]);
            expected[1] = Integer.parseInt(firstLine[1]);
        }catch(Exception e){
            System.out.print("error");
        }
        return expected;
    }
    public String getAnsString(File file){
        String expected = "";
        try{
            Scanner sc  = new Scanner(file);
            expected = sc.nextLine();

        }catch(Exception e){
            System.out.print("error");
        }
        return expected;
    }

}