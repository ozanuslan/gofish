import java.util.Random;

public class Game {
    private Deck mainCards;
    private Deck player;
    private Deck computer;
    private Deck table;
    private final int playerDeckSize = 7;
    private final int totalDeckSize = 24;
    private Random rnd = new Random();

    public Game() {

        dealCards();
    }

    public void dealCards() {
        mainCards = new Deck(totalDeckSize);
        Stack playerStack = new Stack(playerDeckSize);
        Stack computerStack = new Stack(playerDeckSize);
        Stack tableStack = new Stack(totalDeckSize - playerDeckSize);

        int rndNum;
        while (!mainCards.getDeck().isFull()) {
            rndNum = 1 + rnd.nextInt(6);
            if (mainCards.countCard(rndNum) < 4) {
                mainCards.getDeck().push(rndNum);
            }
        }

        while (!playerStack.isFull() && !computerStack.isFull() && !mainCards.getDeck().isEmpty()) {
            playerStack.push(mainCards.getDeck().pop());
            computerStack.push(mainCards.getDeck().pop());
        }

        while (!mainCards.getDeck().isEmpty() && !tableStack.isFull()) {
            tableStack.push(mainCards.getDeck().pop());
        }

        player = new Deck("Player", playerStack);
        computer = new Deck("Computer", computerStack);
        table = new Deck(tableStack);
    }

    public void play() {

    }
}