import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner;
    private static List<Player> players;
    private static List<WarPlayer> warplayers;
    private static List<UnoPlayer> unoplayers;
    private static List<BlackjackPlayer> blackjackplayers;

    public static void main(String[] args) {


        scanner = new Scanner(System.in);

        players = new ArrayList<>();
        warplayers = new ArrayList<>();
        unoplayers = new ArrayList<>();
        blackjackplayers = new ArrayList<>();

        Game warGame = new WarGame(warplayers);
        Game unoGame = new UnoGame(unoplayers);
        Game blackjackGame = new BlackjackGame(blackjackplayers);

        System.out.println("Select a game to play:");
        System.out.println("1. War (*Note: only for 2 players)");
        System.out.println("2. Uno");
        System.out.println("3. Blackjack");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
        int choice = 0;
        try {
            choice = scanner.nextInt();
        }catch (Exception e){
            System.out.println("Error caught " + e);
            System.out.println("try again");
        }

        Game selectedGame = null;
        switch (choice) {
            case 1 -> {
                selectedGame = warGame;
                players_details(2);
                for (Player player:players){
                    WarPlayer player1 = new WarPlayer(player.getName());
                    warplayers.add(player1);
                }
            }
            case 2 -> {
                selectedGame = unoGame;
                setPlayers_details();
                for (Player player:players){
                    UnoPlayer player1 = new UnoPlayer(player.getName());
                    unoplayers.add(player1);
                }
            }
            case 3 -> {
                selectedGame = blackjackGame;
                setPlayers_details();
                for (Player player:players){
                    BlackjackPlayer player1 = new BlackjackPlayer(player.getName());
                    blackjackplayers.add(player1);
                }
            }
            case 4 -> {
                System.out.println("Exiting the game system");
                System.exit(0);
            }
            default -> {
                System.out.println("Invalid choice");
                main(null);
            }
        }

        selectedGame.start();

    }

    public static void setPlayers_details(){
        System.out.print("Enter the number of players: ");
        try {
            int numPlayers = scanner.nextInt();
            switch (numPlayers) {
                case 1,2,3,4,5,6,7,8 -> {
                    players_details(numPlayers);
                }
                default -> {
                    System.out.println("please try number between 2 to 8");
                    main(null);
                }
            }
        }
        catch (Exception e){
            System.out.println("Caught an exception " + e);
            main(null);
        }
    }

    public static void players_details(int numPlayers){
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter the name of player " + i + ": ");
            try{
                String playerName = scanner.next();
                Player player = new Player(playerName);
                players.add(player);
            }
            catch (Exception e){
                System.out.println("Error caught " + e);
            }
        }
    }
}
