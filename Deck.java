import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Deck{
    private List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
    }
    public void createDeck(){}
    public void shuffle() {
        Collections.shuffle(cards);
    }

    public void setCards(Card card){
        cards.add(card);
    }
    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }

    public int getSize() {
        return cards.size();
    }
}

