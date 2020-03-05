import java.util.Random;
import java.util.Scanner;

public class Game {
    private Deck mainCards;
    private Deck player;
    private Deck computer;
    private Deck table;
    private final int playerStartingDeckSize = 7;
    private final int totalDeckSize = 24;
    private final int playerDeckSize = totalDeckSize;
    private final int cardRankLimit = 6;
    private int turnCount = 1;
    private Random rnd = new Random();
    private Scanner sc = new Scanner(System.in);

    public Game() {
        dealCards();
    }

    public void dealCards() {
        mainCards = new Deck(totalDeckSize);
        Stack playerStack = new Stack(playerDeckSize);
        Stack computerStack = new Stack(playerDeckSize);
        Stack tableStack = new Stack(totalDeckSize - playerStartingDeckSize);

        int rndNum;
        while (!mainCards.getDeck().isFull()) {
            rndNum = 1 + rnd.nextInt(cardRankLimit);
            if (mainCards.countCard(rndNum) < 4) {
                mainCards.getDeck().push(rndNum);
            }
        }

        int iter = 0;
        while (iter < playerStartingDeckSize && !mainCards.getDeck().isEmpty()) {
            playerStack.push(mainCards.getDeck().pop());
            computerStack.push(mainCards.getDeck().pop());
            iter++;
        }

        while (!mainCards.getDeck().isEmpty() && !tableStack.isFull()) {
            tableStack.push(mainCards.getDeck().pop());
        }

        player = new Deck("You", playerStack);
        computer = new Deck("Computer", computerStack);
        table = new Deck(tableStack);
    }

    public void transferCards(Deck from, Deck to, int targetCard) {
        Stack temp = new Stack(from.getDeck().size());
        int cardPop;
        while (!from.getDeck().isEmpty()) {
            cardPop = (int) from.getDeck().pop();
            if (cardPop == targetCard) {
                to.getDeck().push(cardPop);
            } else {
                temp.push(cardPop);
            }
        }

        while (!temp.isEmpty()) {
            from.getDeck().push(temp.pop());
        }
    }

    public int takeChoice() {
        boolean isRightAnswer;
        int choice = 0;
        isRightAnswer = false;
        while (!isRightAnswer) {
            player.displayName();
            System.out.print(" ask: ");
            choice = sc.nextInt();
            if (choice > 0 && choice < 7 && player.hasCard(choice)) {
                isRightAnswer = true;
            }
        }
        return choice;
    }

    public void play() {
        boolean isFinished = false;
        boolean turn = true; // True for player False for computer
        int choice;
        while (!isFinished) {
            System.out.println("Turn: " + turnCount + "              Table");
            player.displayName();
            player.displayDeck();
            System.out.print("Book: ");
            player.getBook();
            table.displayDeck();
            System.out.println();
            computer.displayName();
            computer.displayDeck();
            System.out.print("Book: ");
            computer.getBook();
            System.out.println();
            System.out.println();
            if (turn) {
                choice = takeChoice();
                if (computer.hasCard(choice)) {
                    transferCards(computer, player, choice);
                    if(player.hasBook()){//COMPLETE HASBOOK
                        player.incrementBookCount();
                        //CLEAN THE CARDS WHEN BOOKED
                    }
                } else {
                    computer.displayName();
                    System.out.println("says \"Go Fish\"");
                    player.getDeck().push(table.getDeck().pop());
                    if(player.hasBook()){//COMPLETE HASBOOK
                        player.incrementBookCount();
                        //CLEAN THE CARDS WHEN BOOKED
                    }
                    turn = false;
                }
            } else {

            }
            if (player.getDeck().size() == 0 || computer.getDeck().size() == 0) {
                isFinished = true;
            }
            turnCount++;
        }
    }
}