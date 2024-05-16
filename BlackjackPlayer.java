public class BlackjackPlayer extends WarPlayer{
    private int bet;
    public BlackjackPlayer(String name) {
        super(name);
        this.bet = 0;
    }
    public void setBet(int bet) {
        this.bet = bet;
    }
    public int getBet() {
        return bet;
    }
}
