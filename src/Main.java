import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private List<Player> players;
    private Playground pg;

    public static void main(String[] args) {
        initializeGame();
    }

    private static Playground initializeGame() {
        // get inputs
        System.out.print("Enter number of players: ");
        int n = sc.nextInt();
        if (n < 2) {
            endGame("Invalid number of players", true);
        }
        System.out.print("Enter playground dimensions (m*k): ");
        int m = sc.nextInt(), k = sc.nextInt();
        if (m < 1 || k < 1) {
            endGame("Invalid playground dimenstions", true);
        }

        return null;
    }

    private static void endGame(String reason, boolean isError) {
        System.out.printf("%s, the game will stop now%n", reason);
        System.exit(isError ? -1 : 0);
    }
}