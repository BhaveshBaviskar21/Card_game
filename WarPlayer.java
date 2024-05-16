public class WarPlayer extends Player{
    private int score;
    public WarPlayer(String name) {
        super(name);
        this.score = 0;
    }
    public int getScore() {
        return score;
    }
    public void increaseScore(int points) {
        score += points;
    }
}
