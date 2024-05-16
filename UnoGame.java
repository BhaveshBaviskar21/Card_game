import java.util.List;
import java.util.Scanner;

public class UnoGame implements Game {
    private Deck deck;
    private List<UnoPlayer> players;
    private Card openCard;
    private boolean cycle;
    private int playerIndex;
    private String color;
    private boolean playerContinue;
    Scanner scanner = new Scanner(System.in);
    public UnoGame(List<UnoPlayer> players) {
        this.players = players;
        this.deck = new UnoDeck();
        deck.createDeck();
    }

    @Override
    public void start() {
        playerContinue = false;
        cycle = true;
        playerIndex = 0;
        System.out.println("Starting Uno Game");
        for (UnoPlayer player : players) {
            dealInitialCards(player);
        }
        openCard = deck.drawCard();
        while (!isGameFinished()) {
            playTurn();
        }
        determineWinner();

    }

    private void dealInitialCards(UnoPlayer player) {
        for (int i = 0; i < 7; i++) {
            Card card = deck.drawCard();
            player.addCardToHand(card);
        }
    }

    public void playTurn(UnoPlayer player) {
        if(openCard.getValue() == 13){
            System.out.println("Current card: +4 of " + color);
        } else if (openCard.getValue() == 14) {
            System.out.println("Current card: Switch of " + color);
        }else {
            System.out.println("Current card: " + openCard);
        }
        System.out.println("Player " + player.getName() + "`s turn");
        int i = 0;
        for(Card card:player.getHand()){
            System.out.println(i + ". " + card);
            i++;
        }
        System.out.println(i + ". Draw card");
        if(playerContinue){
            System.out.println("input 999 to continue if needed");
        }
        System.out.print("Enter your choice: ");
        int numChoice = 0;
        try{
            numChoice = scanner.nextInt();
        }catch (Exception e){
            System.out.println("Caught an exception " + e);
        }
        if(playerContinue && numChoice == 999){
            playerContinue = false;
            updateplayerindex();
        }else if(numChoice >(i) || numChoice < 0){
            System.out.println("Please choose a correct number");
            playTurn();
        }
        else{
            if(numChoice == (i)){
                Card card = deck.drawCard();
                player.addCardToHand(card);
                if(!(card.getValue()==openCard.getValue()||card.getSuit().equals(openCard.getSuit())||card.getValue()==13||card.getValue()==14)){
                    updateplayerindex();
                }else{
                    playerContinue = true;
                }
            }
            else{
                Card card_played = player.getCardinHand(numChoice);
                System.out.println("Player " + player.getName() + " played " + card_played);
                checkCardtoplay(card_played);
                playerContinue = false;
            }
        }
        clearFor2nd();
    }

    public void playTurn(){
        playTurn(players.get(playerIndex));
    }

    private void checkCardtoplay(Card card){
        String suit = "";
        if(openCard.getValue() == 13 || openCard.getValue() == 14){
            suit = color;
        }else {
            suit = openCard.getSuit();
        }

        int value = openCard.getValue();
        String playedSuit = card.getSuit();
        int playedValue = card.getValue();
        switch (playedValue){
            case 0,1,2,3,4,5,6,7,8,9 ->{
                if(playedValue == value || suit.equals(playedSuit)){
                    deck.setCards(openCard);
                    openCard = card;
                    updateplayerindex();
                }else{
                    System.out.println("Played card " + card + " not possible try again");
                    players.get(playerIndex).addCardToHand(card);
                }
            }
            case 10 ->{
                if(suit.equals(playedSuit)){
                    //+2
                    deck.setCards(openCard);
                    openCard = card;
                    UnoPlayer player = getNextPlayer();
                    for(int i =0;i<2;i++){
                        player.addCardToHand(deck.drawCard());
                    }
                    updateplayerindex();
                }
                else{
                    System.out.println("Played card " + card + " not possible try again");
                    players.get(playerIndex).addCardToHand(card);
                }
            }
            case 11 ->{
                if(playedValue == value || suit.equals(playedSuit)){
                    //reverse
                    deck.setCards(openCard);
                    openCard = card;
                    cycle = false;
                    updateplayerindex();
                    updateplayerindex();
                }else{
                    System.out.println("Played card " + card + " not possible try again");
                    players.get(playerIndex).addCardToHand(card);
                }
            }
            case 12 ->{
                if(playedValue == value || suit.equals(playedSuit)){
                    //skip
                    deck.setCards(openCard);
                    openCard = card;
                    updateplayerindex();
                    updateplayerindex();
                }else{
                    System.out.println("Played card " + card + " not possible try again");
                    players.get(playerIndex).addCardToHand(card);
                }

            }
            case 13 -> {
                //+4
                deck.setCards(openCard);
                openCard = card;
                UnoPlayer player = getNextPlayer();
                for(int i =0;i<4;i++){
                    player.addCardToHand(deck.drawCard());
                }
                changeCardColor();
                updateplayerindex();
            }
            case 14 -> {
                //switch
                deck.setCards(openCard);
                openCard = card;
                changeCardColor();
                updateplayerindex();
            }
        }
    }
    private void changeCardColor(){
        System.out.println("1. Red");
        System.out.println("2. Blue");
        System.out.println("3. Green");
        System.out.println("4. Yellow");
        System.out.print("Choose the next color: ");
        int colorint = 0;
        try{
            colorint = scanner.nextInt();
        }catch (Exception e){
            System.out.println("Caught an exception " + e);
        }
        switch (colorint){
            case 1 ->{
                color = "Red";
            }
            case 2 ->{
                color = "Blue";
            }
            case 3 ->{
                color = "Green";
            }
            case 4 ->{
                color = "Yellow";
            }
        }
    }
    private UnoPlayer getNextPlayer() {
        int nextPlayerIndex;
        if (cycle) {
            nextPlayerIndex = (playerIndex + 1) % players.size();
        } else {
            nextPlayerIndex = (playerIndex - 1 + players.size()) % players.size();
        }
        return players.get(nextPlayerIndex);
    }
    private void updateplayerindex() {
        if (cycle) {
            playerIndex = (playerIndex + 1) % players.size();
        } else {
            playerIndex = (playerIndex - 1 + players.size()) % players.size();
        }
    }

    private boolean isGameFinished() {
        for (UnoPlayer player : players) {
            if (player.getHand().isEmpty()) {
                return true;
            }else if(player.getHand().size() == 1){
                System.out.println("Enter 'UNO' for calling uno");
                String unoCall = scanner.next();
                if(unoCall.equals("UNO") || unoCall.equals("uno")){
                    player.setUnoCalled(true);
                }
            } else if (player.getHand().size() == 1 && !player.getUnoCalled()) {
                for(int i =0;i<4;i++){
                    Card card = deck.drawCard();
                    player.addCardToHand(card);
                }
            }
        }
        return false;
    }

    private void determineWinner() {
        for (UnoPlayer player : players) {
            if (player.getHand().isEmpty()) {
                System.out.println("Player " + player.getName() + " wins!");
                System.out.println("Select option");
                System.out.println("1. Start again");
                System.out.println("2. exit");
                int scans = scanner.nextInt();
                switch (scans){
                    case 1 -> start();
                    case 2 -> System.exit(0);
                    default -> System.out.println("Invalid choice. Exiting...");
                }
            }
        }
    }

    private void clearFor2nd(){
        for(int i = 0;i<40;i++){
            System.out.println();
        }
    }
}
