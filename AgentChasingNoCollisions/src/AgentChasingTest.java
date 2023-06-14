import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
class AgentChasingTest {

//    @Test
//    void solveMakeSpan() throws FileNotFoundException {
//        File dir = new File("src/cooperative multi-agent chasing/makespan");
//        testFolderMakeSpan(dir);
//    }
    @Test
    void solveMinCostNoCol() throws FileNotFoundException {
        File dir = new File("src/cooperative mapf with no collisions/mincost");
        testFolder(dir);
    }

//    private void testFolderMakeSpan(File dir) throws FileNotFoundException {
//        File[] directoryListing = dir.listFiles();
//
//        int testCount = 0, correct = 0;
//
//
//        for(int i = 0; i < directoryListing.length; i += 3){
//            File ans = directoryListing[i];
//
//            File in = directoryListing[i + 1];
//
//            Scanner scan = new Scanner(in);
//
//            int expected = getAns(ans);
//            AgentChasing ac = new AgentChasing(scan);
//            int res = ac.solveMakeSpan();
//
//            if(expected == res){
//                correct++;
//            }else{
//                System.out.print(in.getName());
//            }
//            testCount++;
//
//            System.out.print("\033[2J");
//            System.out.println(testCount + "/40");
//        }
//
//
//        System.out.println("\n------------------------\n");
//        System.out.println("Passed: " + correct + "/" + testCount);
//    }

    @Test
    void solveMinCost() throws FileNotFoundException {
        File dir = new File("src/cooperative multi-agent chasing/mincost");
        testFolder(dir);
    }

    private  void testFolder(File dir) throws FileNotFoundException {
        File[] directoryListing = dir.listFiles();

        int testCount = 0, correct = 0;


        for(int i = 0; i < directoryListing.length; i += 3){
            File ans = directoryListing[i];
            System.out.println(ans.getName());
            File in = directoryListing[i + 1];
            System.out.println(in.getName());
            Scanner scan = new Scanner(in);

            int expected = getAns(ans);
            AgentChasing ac = new AgentChasing(scan);
            int res = ac.solveMinCostNoCol();

            if(expected == res){
                correct++;
            }else{
                System.out.println(in.getName() + "incorrect" + "actual " + res + " expected " + expected);
            }
            testCount++;

            System.out.print("\033[2J");
            System.out.println(testCount + "/40");
        }


        System.out.println("\n------------------------\n");
        System.out.println("Passed: " + correct + "/" + testCount);

    }
    public  int getAns(File file)  {

        int expected = 0 ;
        try{
            Scanner sc  = new Scanner(file);
            String[] firstLine = sc.nextLine().split(" ");
            expected = Integer.parseInt(firstLine[0]);

        }catch(Exception e){
            System.out.print("error");
        }
        return expected;
    }
}