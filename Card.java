public class Card{
    private String rank;
    private String suit;
    private int value;

    public Card(String rank, String suit, int value) {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    public String getSuit() {
        return suit;
    }
    /*public void setSuit(String newSuit){
        suit = newSuit;
    }
    public String getRank() {
        return rank;
    }*/
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
