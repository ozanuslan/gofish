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

    void dealCards() {
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

    void transferCards(Deck from, Deck to, int targetCard) {
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

    boolean tryParseInt(String value) {  
        try {  
            Integer.parseInt(value);  
            return true;  
         } catch (NumberFormatException e) {  
            return false;  
         }  
    }

    void displayTurn(){
        System.out.println("Turn: " + turnCount);
        table.displayName();
        System.out.print("= ");
        table.displayDeck();
        System.out.println();
        player.displayName();
        System.out.print("= ");
        player.displayDeck();
        System.out.print("Book: ");
        System.out.print(player.getBook() + "  ");
        System.out.println();
        computer.displayName();
        System.out.print("= ");
        computer.displayDeck();
        System.out.print("Book: ");
        System.out.println(computer.getBook());
        System.out.println();
    }    

    int takeChoice() {
        boolean isRightAnswer;
        int choice = 0;
        String control;
        isRightAnswer = false;
        while (!isRightAnswer) {
            player.displayName();
            System.out.print("ask: ");
            control = sc.nextLine();
            if(tryParseInt(control)){
                choice = Integer.parseInt(control);
            }
            if (choice > 0 && choice < 7 && player.hasCard(player.getDeck(), choice)) {
                isRightAnswer = true;
            }
        }
        return choice;
    }

    void play() {
        boolean isFinished = false;
        boolean turn = true; // True for player False for computer
        int choice;
        while (!isFinished) {
            displayTurn();
            if (turn) {
                choice = takeChoice();
                if (computer.hasCard(computer.getDeck(), choice)) {
                    transferCards(computer, player, choice);
                    if (player.hasBook()) {
                        player.incrementBookCount();
                        player.cleanBookedCards();
                    }
                } else {
                    computer.displayName();
                    System.out.println("says \"Go Fish\"");
                    player.getDeck().push(table.getDeck().pop());
                    if (player.hasBook()) {
                        player.incrementBookCount();
                        player.cleanBookedCards();
                    }
                    turn = false;
                }
            } else {
                choice = computer.pickRandomCard();
                computer.displayName();
                System.out.println("asks: " + choice);
                if (player.hasCard(player.getDeck(), choice)) {
                    transferCards(player, computer, choice);
                    if (computer.hasBook()) {
                        computer.incrementBookCount();
                        computer.cleanBookedCards();
                    }
                } else {
                    player.displayName();
                    System.out.println("say \"Go Fish\"");
                    computer.getDeck().push(table.getDeck().pop());
                    if (computer.hasBook()) {
                        computer.incrementBookCount();
                        computer.cleanBookedCards();
                    }
                    turn = true;
                }
            }
            System.out.println();
            if (player.getDeck().size() == 0 || computer.getDeck().size() == 0) {
                isFinished = true;
            }
            
            turnCount++;
        }

        //Display the final state of the game  
        displayTurn();

        if (player.getBook() > computer.getBook()) {
            player.displayName();
            System.out.println("win!");
        }
        else if (player.getBook() < computer.getBook()){
            computer.displayName();
            System.out.println("wins!");
        }
        else{
            System.out.println("Tie!");
        }
    }
}