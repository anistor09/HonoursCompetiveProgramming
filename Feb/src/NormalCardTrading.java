import java.util.Scanner;

public class NormalCardTrading {
    int traders;
    int cardsNumber;
    int[][] matrix;
    private int player;
    private int minCards;
    private int minTrader;

    public int solve(Scanner sc){
        readInput(sc);

        int sum = minimumCollectionsPerPlayer();
        this.minCards = minimumCollectionsPerCard();

        return Math.min(this.minCards,sum);
    }
    public int solveFair(){

        this.minTrader = Integer.MAX_VALUE;
        solveTradersFair();
        this.minCards = minimumCollectionsPerCard();
        return Math.min(minCards / this.traders, minTrader);

    }

    private int solveTradersFair() {


        for(int i = 0; i < traders; i++){

            int playerCards = 0;

            for(int j = 0; j < cardsNumber; j++){
                playerCards += matrix[i][j];
            }

            this.minTrader = Math.min((playerCards/ cardsNumber), this.minTrader);

        }

        return this.minTrader;
    }


    private int minimumCollectionsPerCard() {
        int miniCollections = Integer.MAX_VALUE/2;

            for(int j = 0; j < cardsNumber; j++){

            int cardCollections = 0;

            for(int i = 0; i < traders; i++){
                cardCollections += matrix[i][j];
            }
            if(cardCollections < miniCollections)
                miniCollections = cardCollections;

        }
        return miniCollections ;
    }

    private int minimumCollectionsPerPlayer(){

        int sum = 0;

        for(int i = 0; i < traders; i++){

            int playerCards = 0;

            for(int j = 0; j < cardsNumber; j++){
                playerCards += matrix[i][j];
            }
                sum += (playerCards/ cardsNumber);
            }

        return sum;
    }


    private void readInput(Scanner sc){
        String[] firstLine = sc.nextLine().split(" ");
        this.traders = Integer.parseInt(firstLine[0]);
        this.cardsNumber = Integer.parseInt(firstLine[1]);
        this.matrix = new int[traders][cardsNumber];
        int i = 0;
        while(sc.hasNextLine()){

            String[] line = sc.nextLine().split(" ");
            for(int j = 0; j< line.length; j++){
                int value = Integer.parseInt(line[j]);
                matrix[i][j] = value ;
            }
            i++;
        }
    }


}
