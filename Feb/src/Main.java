import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) throws FileNotFoundException {
            Scanner sc = new Scanner(System.in);
            NormalCardTrading nc = new NormalCardTrading();
            System.out.print(nc.solve(sc) + " " + nc.solveFair() + "\n");

    }


}
