import java.util.Random;

public class Deck {
    private String name;
    private Stack deck;
    private int book;
    private Random rnd = new Random();

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

    public void displayDeck(){
        Stack temp = new Stack(deck.size());
        int cardPop;
        while (!deck.isEmpty()) {
            cardPop = (int) deck.pop();
            System.out.print(cardPop+" ");
            temp.push(cardPop);
        }
        
        while (!temp.isEmpty()) {
            deck.push(temp.pop());
        }
    }

    public int pickRandomCard(){
        int rndCard = 0;
        boolean doesExist = false;
        while(!doesExist){
            rndCard = 1+rnd.nextInt(6);
            if(hasCard(deck, rndCard)){
                doesExist = true;
            }
        }
        return rndCard;
    }

    public boolean hasCard(Stack deck, int targetCard) {
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

    public boolean hasBook(){
        boolean hasBook = false;
        Stack temp1 = new Stack(deck.size());
        Stack temp2 = new Stack(deck.size());
        int cardPop;
        while(!deck.isEmpty()){
            cardPop = (int)deck.pop();
            if(!hasCard(temp1, cardPop)){
                temp1.push(cardPop);
            }
            temp2.push(cardPop);
        }
        while(!temp2.isEmpty()){
            deck.push(temp2.pop());
        }

        int count = 0;
        int uniqueCard;
        while(!temp1.isEmpty() && count < 4){
            count = 0;
            uniqueCard = (int)temp1.pop();
            while(!deck.isEmpty()){
                cardPop = (int)deck.pop();
                if(cardPop == uniqueCard){
                    count++;
                }
                temp2.push(cardPop);
            }
            while(!temp2.isEmpty()){
                deck.push(temp2.pop());
            }
        }
        if(count == 4){
            hasBook = true;
        }
        return hasBook;
    }

    public void cleanBookedCards(){
        Stack temp1 = new Stack(deck.size());
        Stack temp2 = new Stack(deck.size());
        int cardPop;
        while(!deck.isEmpty()){
            cardPop = (int)deck.pop();
            if(!hasCard(temp1, cardPop)){
                temp1.push(cardPop);
            }
            temp2.push(cardPop);
        }
        while(!temp2.isEmpty()){
            deck.push(temp2.pop());
        }

        int count = 0;
        int uniqueCard;
        int cleaningCard = 0;
        while(!temp1.isEmpty() && count < 4){
            count = 0;
            uniqueCard = (int)temp1.pop();
            while(!deck.isEmpty()){
                cardPop = (int)deck.pop();
                if(cardPop == uniqueCard){
                    count++;
                }
                temp2.push(cardPop);
            }
            while(!temp2.isEmpty()){
                deck.push(temp2.pop());
            }
            if(count == 4){
                cleaningCard = uniqueCard;
            }
        }

        while(!deck.isEmpty()){
            cardPop = (int)deck.pop();
            if(cardPop != cleaningCard){
                temp2.push(cardPop);
            }
        }
        while(!temp2.isEmpty()){
            deck.push(temp2.pop());
        }
    }
}