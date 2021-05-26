package MahJava;

import MahJava.game.Game;
import MahJava.player.Player;
import org.testng.internal.collections.Pair;

import java.util.*;

public class Main {
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static List<Pair<String, String>> instantiateProfiles() {
        return Arrays.asList(
                new Pair<>("Eager", "Always try to do a combination if it can"),
                new Pair<>("Concealed", "Choose to never draw discarded tiles and always opt for concealed hands"),
                new Pair<>("Composed", "Make decisions based on clever pondering of the game state"),
                new Pair<>("Mixed", "Switch strategies on the fly, depending of win/lose conditions")
        );
    }

    public static void main(String[] args) {
        boolean wantsToPlay = true;
        List<Pair<String, String>> availableProfiles = instantiateProfiles();
        List<Player> players = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (wantsToPlay) {
            clearScreen();
            System.out.println("Welcome to MahJava!");
            if (players.size() == 4) {
                System.out.println("Do you want the same agents as last game? [Y/(n)]");
                System.out.print("> ");
                String line = scanner.nextLine().toLowerCase(Locale.ROOT);
                if (line.length() == 0 || line.charAt(0) != 'y') {
                    players.clear();
                    System.out.println("Choose the 4 agents that will play a Mahjong Game!");
                }
            }

            while (players.size() != 4) {
                System.out.println("Pick a profile: " + players.size() + "/4 selected.");
                System.out.println("Available profiles: ");
                for (Pair<String, String> entry : availableProfiles) {
                   System.out.println("\t- " + entry.first() + ": " +
                           entry.second());
                }
                System.out.println("Please write the name of the profile you would like to pick");
                System.out.print("> ");
                String picked = scanner.nextLine().toUpperCase(Locale.ROOT);
                switch (picked) {
                    case "EAGER":
                        players.add(Player.EagerPlayer(picked));
                        break;
                    case "CONCEALED":
                        players.add(Player.ConcealedPlayer(picked));
                        break;
                    case "COMPOSED":
                        players.add(Player.ComposedPlayer(picked));
                        break;
                    case "MIXED":
                        players.add(Player.MixedPlayer(picked));
                        break;
                }
            }

            Game game = new Game(players);
            game.gameLoop();

            System.out.println("Would you like to play another game? [Y/(n)]");
            System.out.print("> ");
            String line = scanner.nextLine().toLowerCase(Locale.ROOT);
            if (line.length() == 0 || line.charAt(0) != 'y') {
                wantsToPlay = false;
            }
        }

        System.out.println("Goodbye and thank you for playing MahJava!");
    }
}
