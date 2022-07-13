import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Playground playground = initializeGame();
        while (startGame(playground)) {
            playGame(playground);
            System.out.println(playground.getStats());
        }
        endGame("Have a good day!", false);
    }

    private static Playground initializeGame() {
        // get inputs
        System.out.print("Enter number of players: ");
        int n = sc.nextInt();
        if (n < 2) {
            endGame("Invalid number of players", true);
        }

        // create players
        List<Player> players = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            players.add(new Player(String.format("Player%s", i + 1)));
        }

        // create playground
        return new Playground(players);
    }

    private static void endGame(String reason, boolean isError) {
        System.out.printf("%s, the game will stop now%n", reason);
        System.exit(isError ? -1 : 0);
    }

    private static boolean startGame(Playground pg) {
        System.out.print("Start a new game (y/n)? ");
        while (true) {
            String reply = sc.next();
            if (reply.equalsIgnoreCase("y")) break;
            if (reply.equalsIgnoreCase("n")) return false;
        }

        System.out.print("Enter playground square dimensions (m*k): ");
        int m = sc.nextInt(), k = sc.nextInt();
        if (m < 1 || k < 1) {
            endGame("Invalid playground dimensions", true);
        }

        pg.setDimensions(m, k);
        return true;
    }

    private static void playGame(Playground pg) {
        pg.reset();
        System.out.println(pg.getVisualRepresentation());
        int status = 0;
        while (status != -1) {
            System.out.printf("%s's turn, enter dash dimensions (o,x,y): ", pg.getPlayerToRollName());
            status = pg.play(sc.next(), sc.nextInt(), sc.nextInt());
            switch (status) {
                case 1 -> System.out.println("Invalid dash, please choose another one...");
                case 2 -> System.out.println("Dash already occupied, please choose another one...");
                case 3, -1 -> System.out.println("Occupied some squares!");
            }
            System.out.println(pg.getVisualRepresentation());
        }
        System.out.println(pg.calculateResults());
    }
}