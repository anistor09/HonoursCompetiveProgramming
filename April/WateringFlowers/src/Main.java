import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) throws FileNotFoundException {
          Scanner sc = new Scanner(System.in);
          System.out.print(mainCalculation(sc) + "\n");
    }


    public static int mainCalculation(Scanner rows) {
        int n = Integer.parseInt(rows.nextLine());
        String[] strings = rows.nextLine().split(" ");
        int[] seq= new int[n];
        for(int i = 0; i< n; i++)
            seq[i] = Integer.parseInt(strings[i]);
        int rest = (int)Solution.countInversions(seq)%(1000000007);
        return rest;

    }
}
