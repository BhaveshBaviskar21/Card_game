import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WarGame implements Game {
    private Deck deck;
    private List<WarPlayer> players;
    private List<Card> played_card_value = new ArrayList<>();

    public WarGame(List<WarPlayer> players) {
        this.players = players;
        this.deck = new StandardDeck();
        deck.createDeck();
    }

    @Override
    public void start() {
        System.out.println("Starting War Game");
        Scanner scanner = new Scanner(System.in);

        dealInitialCards();

        // Game loop
        while (!isGameFinished()) {
            for(WarPlayer player:players){
                System.out.println("Player " + player.getName() + " has " + player.getHand().size() + " cards, " + player.getScore() + " points");
            }
            System.out.println("Select option");
            System.out.println("1. Start/Continue");
            System.out.println("2. exit");
            int scans = scanner.nextInt();
            switch (scans){
                case 1 -> playTurn();
                case 2 -> System.exit(0);
                default -> System.out.println("Invalid choice. Exiting...");
            }
        }

        determineWinner();
    }

    private void dealInitialCards() {
        for (int i = 0; i < 52; i++) {
            for (WarPlayer player : players) {
                Card card = deck.drawCard();
                if(card != null){
                    player.addCardToHand(card);
                }
            }
        }
    }

    public void playTurn(WarPlayer player) {
        System.out.println("Player " + player.getName() + "'s turn");
        Card card = player.playCard();
        System.out.println("Player draws: " + card);
        played_card_value.add(card);
    }

    private void playTurn() {

        for (WarPlayer player : players) {
            playTurn(player);
        }

        Card first_player_card = played_card_value.get(0);
        Card second_player_card = played_card_value.get(1);
        compare_card(first_player_card,second_player_card);

    }

    private void compare_card(Card Card, Card card2){
        if(Card.getValue()>card2.getValue()){
            getopponentcard(players.get(0));
            System.out.println();
            System.out.println("Player " + players.get(0).getName() + " wins this round");
            System.out.println();
            System.out.println();
            System.out.println("---------*---------");
        } else if (card2.getValue()>Card.getValue()) {
            getopponentcard(players.get(1));
            System.out.println();
            System.out.println("Player " + players.get(1).getName() + " wins this round");
            System.out.println();
            System.out.println();
            System.out.println("---------*---------");
        }else if (Card.getValue() == card2.getValue()){
            System.out.println("WAR Started");
            war_start();
        }
    }

    private void getopponentcard(WarPlayer player){
        for (Card card: played_card_value){
            player.addCardToHand(card);
        }
        played_card_value.clear();
        player.increaseScore(1);
    }

    private void war_start(){
        for (WarPlayer player:players){
            if(player.getHand().size()>3){
                for(int i = 0;i<3;i++){
                    Card card = player.playCard();
                    played_card_value.add(card);
                }
            }else if(player.getHand().size() ==1){
                System.out.println("Player " + player.getName() + "'s turn");
                Card card = player.playCard();
                System.out.println("Player draws: " + card);
                played_card_value.add(card);
                break;
            }else{
                for(int i = 0;i<(player.getHand().size()-1);i++){
                    Card card = player.playCard();
                    played_card_value.add(card);
                }
            }
            System.out.println("Player " + player.getName() + "'s turn");
            Card card = player.playCard();
            System.out.println("Player draws: " + card);
            played_card_value.add(card);
        }
        int half_list = played_card_value.size()/2;
        System.out.println(played_card_value.get(half_list) + "  "+ played_card_value.get(half_list+half_list-1));
        compare_card(played_card_value.get(half_list),played_card_value.get(half_list+half_list-1));
    }

    private boolean isGameFinished() {
        for (WarPlayer player : players) {
            if (player.getHand().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void determineWinner() {
        int maxCards = -1;
        WarPlayer winner = null;

        for (WarPlayer player : players) {
            int numCards = player.getHand().size();
            if (numCards > maxCards) {
                maxCards = numCards;
                winner = player;
            }
        }
        System.out.println("Player " + winner.getName() + " wins with " + maxCards + " cards remaining!");
    }
}
