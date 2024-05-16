import java.util.List;

public class StandardDeck extends Deck{

    public void createDeck() {

        String[] suits = {"♤", "♡", "♢", "♧"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (String suit : suits) {
            for (int i = 0;i<13;i++) {
                Card card = new Card(ranks[i], suit,i);
                setCards(card);
            }
        }
        shuffle();
    }
}
