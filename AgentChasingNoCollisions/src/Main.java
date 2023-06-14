
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) throws FileNotFoundException {
//      Scanner sc = new Scanner( new File("src/cooperative multi-agent chasing/makespan/xl-sparse-5.in"));
        Scanner sc = new Scanner( System.in);
        AgentChasing ac = new AgentChasing(sc);
        System.out.print(ac.solveMinCostNoCol() + "\n");

    }


}
