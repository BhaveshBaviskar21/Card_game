import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackjackGame implements Game {
    private Deck deck;
    private List<BlackjackPlayer> players;
    private List<BlackjackPlayer> dealers;
    private Scanner scanner;
    private boolean continue_game;

    public BlackjackGame(List<BlackjackPlayer> players) {
        this.players = players;
        this.deck = new StandardDeck();
        deck.createDeck();
        dealers = new ArrayList<>();
    }

    @Override
    public void start() {
        continue_game = true;
        scanner = new Scanner(System.in);
        for(int i = 0;i<players.size();i++){
            BlackjackPlayer player = new BlackjackPlayer("dealer");
            dealers.add(player);
        }
        System.out.println("Starting Blackjack Game");



        while(!gamefinished()){
            playturn();
            reset_game();
        }
    }

    private void dealInitialCards(Player player) {
        for (int i = 0; i < 2; i++) {
            Card card = deck.drawCard();
            player.addCardToHand(card);
        }

    }

    public void playTurn(BlackjackPlayer player) {
        System.out.println("Player " + player.getName() + "'s turn:");
        display(String.valueOf(player.getHand().get(0)), String.valueOf(player.getHand().get(1)));
        System.out.println("Select an option: ");
        System.out.println("1. HIT");
        System.out.println("2. STAND");
        System.out.println("3. Double Down");
        System.out.println("4. Increase BET");
        int numchoice = 0;
        try {
            numchoice = scanner.nextInt();
        }catch (Exception e){
            System.out.println("Error " + e);
        }
        switch (numchoice){
            case 1 -> {
                System.out.println("Player " + player.getName() +" hits");
                playerHits(players.indexOf(player));
            }
            case 2 -> {
                System.out.println("Player stand");System.out.println("Dealer draws");
                display(String.valueOf(dealers.get(players.indexOf(player)).getHand().get(0)),String.valueOf(dealers.get(players.indexOf(player)).getHand().get(1)));
                determineWinner(players.indexOf(player));
            }
            case 3 -> {
                System.out.println("Player double down");
                player.setBet(player.getBet()*2);
                playerHits(players.indexOf(player));
            }
            case 4 -> {
                System.out.println("Increase bet by: ");
                try {
                    int bet = scanner.nextInt();
                    bet = player.getBet()+bet;
                    player.setBet(bet);
                    playerHits(players.indexOf(player));
                }catch (Exception e){
                    System.out.println("Error " + e);
                }
            }
        }

    }
    private void playturn(){
        for(BlackjackPlayer player:players){
            dealInitialCards(player);
        }
        for(BlackjackPlayer dealer:dealers){
            dealInitialCards(dealer);
        }
        for(int i=0;i<players.size();i++){
            startBet(i);
            dealerTurn(i);
        }
    }
    private void dealerTurn(int i){
        System.out.println("Dealer`s hand");
        display(String.valueOf(dealers.get(i).getHand().get(0)),"");
        playTurn(players.get(i));
    }

    private void playerHits(int i){
        BlackjackPlayer player = players.get(i);
        BlackjackPlayer dealer = dealers.get(i);

        Card Card = deck.drawCard();
        player.addCardToHand(Card);

        int dealer_hand_value = 0;
        for(Card card:dealer.getHand()){
            if(card.getValue() == 12){
                dealer_hand_value += 11;
            }else{
                dealer_hand_value = hand_value(card.getValue(),dealer_hand_value);
            }
        }
        if(dealer_hand_value<=17){
            Card card2 =deck.drawCard();
            dealer.addCardToHand(card2);
            System.out.println("Dealer draws");
            display(String.valueOf(dealer.getHand().get(0)),String.valueOf(dealer.getHand().get(1)),String.valueOf(dealer.getHand().get(2)));
        }
        else{
            System.out.println("Dealer draws");
            display(String.valueOf(dealer.getHand().get(0)),String.valueOf(dealer.getHand().get(1)));
        }
        System.out.println("Player`s hand");
        display(String.valueOf(player.getHand().get(0)), String.valueOf(player.getHand().get(1)),String.valueOf(player.getHand().get(2)));
        determineWinner(i);
    }

    private void determineWinner(int i) {
        BlackjackPlayer player = players.get(i);
        BlackjackPlayer dealer = dealers.get(i);
        int dealer_hand_value = 0;
        int ace = 0;
        for(Card card: dealer.getHand()){
            if(card.getValue() == 12){
                ace += 1;
            }
            else {
                dealer_hand_value = hand_value(card.getValue(), dealer_hand_value);
            }
        }
        for(int j = 0;j<ace;j++) {
            if (1 <= (21 - dealer_hand_value) && (21-dealer_hand_value)<=11) {
                dealer_hand_value = 21;
            }else if(dealer_hand_value + 11 < 21){
                dealer_hand_value += 11;
            }else{
                dealer_hand_value += 1;
            }
        }
        int player_hand_value = 0;
        for (Card card : player.getHand()){
            player_hand_value = hand_value(card.getValue(),player_hand_value);
        }
        if (player_hand_value == dealer_hand_value) {
            System.out.println("It's a tie between " + player.getName() + " and the dealer!");
            player.increaseScore(player.getScore()+player.getBet());
        } else if (player_hand_value > 21) {
            System.out.println(player.getName() + " busted! Dealer wins!");
            player.increaseScore(player.getScore()-player.getBet());
        } else if (dealer_hand_value > 21) {
            System.out.println("Dealer busted! " + player.getName() + " wins!");
            player.increaseScore(player.getBet()*2);
        } else if (player_hand_value > dealer_hand_value) {
            System.out.println(player.getName() + " wins!");
            player.increaseScore(player.getBet()*2);
        } else {
            System.out.println("Dealer wins!");
        }
    }
    private void reset_game(){
        for(BlackjackPlayer player:players){
            while (!player.getHand().isEmpty()) {
                Card card = player.playCard();
                deck.setCards(card);
            }
        }
        for(BlackjackPlayer player:dealers){
            while (!player.getHand().isEmpty()) {
                Card card = player.playCard();
                deck.setCards(card);
            }
        }
        deck.shuffle();
    }
    private int hand_value(int value,int returnValue){
        switch (value) {
            case 0, 1, 2, 3, 4, 5, 6, 7, 8 -> {
                returnValue += (value + 2);
            }
            case 9, 10, 11 -> {
                returnValue+= 10;
            }
        }
        return returnValue;
    }

    private void startBet(int i){
        BlackjackPlayer player = players.get(i);
        System.out.print("Enter the staring bet " + player.getName() + ": ");
        try {
            int bet = scanner.nextInt();
            player.setBet(bet);
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }
    private boolean gamefinished(){
        return !continue_game;
    }
    private void display(String... cards){
        int maxCardLength = 0;
        for (String card : cards) {
            if (card.length() > maxCardLength) {
                maxCardLength = card.length();
            }
        }

        int boxWidth = maxCardLength + 4;
        int boxHeight = 5;

        for (int i = 0; i < boxHeight; i++) {
            for (int j = 0; j < cards.length; j++) {
                if (i == 0 || i == boxHeight - 1) {
                    for (int k = 0; k < boxWidth; k++) {
                        System.out.print("-");
                    }
                } else if (j == 0 || j==1 || j==2) {
                    System.out.print("|");
                    for (int k = 0; k < (boxWidth - 2 - cards[j].length()) / 2; k++) {
                        System.out.print(" ");
                    }
                    if(i==boxHeight/2) {
                        System.out.print(cards[j]);
                    }
                    else{
                        for(int k = 0; k < (cards[j].length()); k++){
                            System.out.print(" ");
                        }
                    }
                    for (int k = 0; k < (boxWidth - 2 - cards[j].length()) / 2; k++) {
                        System.out.print(" ");
                    }
                    System.out.print("|");
                } else if (i == boxHeight / 2) {
                    System.out.print("|");
                    for (int k = 0; k < boxWidth - 2; k++) {
                        System.out.print(" ");
                    }
                    System.out.print("|");
                } else {
                    System.out.print("|");
                    for (int k = 0; k < boxWidth - 2; k++) {
                        System.out.print(" ");
                    }
                    System.out.print("|");
                }
            }
            System.out.println();
        }
    }
}
