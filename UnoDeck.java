import java.util.List;

public class UnoDeck extends Deck {
    public void createDeck() {

        String[] colors = {"Red", "Blue", "Green", "Yellow"};
        String[] ranks = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+2", "Reve", "Skip"};
        String[] wilds = {"+4","Switch"};
        int[] values = {13,14};
        for(int i = 0;i<2;i++) {
            for (String color : colors) {
                for (int j = 0;j<13;j++) {
                    Card card = new Card(ranks[j], color,j);
                    setCards(card);
                }
            }
        }
        for(int i = 0;i<2;i++){
            for(int j = 0;j<4;j++){
                Card card = new Card(wilds[i], "Black",values[i]);
                setCards(card);
            }
        }
        shuffle();
    }
}
