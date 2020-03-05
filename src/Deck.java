public class Deck {
    private String name;
    private Stack deck;
    private int book;

    public Deck(String name, Stack deck) {
        this.name = name;
        this.deck = deck;
        this.book = 0;
    }

    public Deck(Stack deck) {
        this.name = "Table";
        this.deck = deck;
    }

    public Deck(int deckSize) {
        this.name = "Main_Deck";
        this.deck = new Stack(deckSize);
    }

    public String getName() {
        return name;
    }

    public void setDeck(Stack deck) {
        this.deck = deck;
    }

    public Stack getDeck() {
        return deck;
    }

    public int getBook() {
        return book;
    }

    public void incrementBookCount() {
        this.book += 1;
    }

    public void displayName(){
        System.out.print(name+" ");
    }

    public boolean hasCard(int targetCard) {
        boolean hasCard = false;
        Stack temp = new Stack(deck.size());
        int cardPop;
        while (!deck.isEmpty() && !hasCard) {
            cardPop = (int) deck.pop();
            if (cardPop == targetCard) {
                hasCard = true;
            }
            temp.push(cardPop);
        }
        while (!temp.isEmpty()) {
            deck.push(temp.pop());
        }
        return hasCard;
    }

    public int countCard(int targetCard){
        int count = 0;
        Stack temp = new Stack(deck.size());
        int cardPop;
        while (!deck.isEmpty()) {
            cardPop = (int) deck.pop();
            if (cardPop == targetCard) {
                count++;
            }
            temp.push(cardPop);
        }
        while (!temp.isEmpty()) {
            deck.push(temp.pop());
        }
        return count;
    }

    public void displayDeck(){
        Stack temp = new Stack(deck.size());
        int cardPop;
        while (!deck.isEmpty()) {
            cardPop = (int) deck.pop();
            System.out.print(cardPop+" ");
            temp.push(cardPop);
        }
        System.out.println();
        while (!temp.isEmpty()) {
            deck.push(temp.pop());
        }
    }
}