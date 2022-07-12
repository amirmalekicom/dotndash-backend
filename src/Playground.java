import java.util.List;

public class Playground {
    private int width, height;
    private List<Player> players;
    private Player playerToRoll;
    private boolean[][] dashBoard;

    public Playground(int width, int height, List<Player> players) {
        this.width = width;
        this.height = height;
        this.players = players;
    }

    public String getPlayerToRollName() {
        return playerToRoll.getName();
    }

    public int play(int x, int y) {
        x--;
        y--;
        if (x < 1 || x >= width || y < 1 || y >= height) {
            return 1;
        }
        if (dashBoard[x][y]) {
            return 2;
        }
        dashBoard[x][y] = true;
        if( checkSurroundingSquares(x, y)) {
           return 3;
        }
        if (checkEndGame()){
            return -1;
        }
        return 0;
    }

    private boolean checkEndGame() {

        return false;
    }

    public void reset() {
        dashBoard = new boolean[width][height];
        playerToRoll = players.get(0);  // TODO: randomize turns
    }

    public boolean checkSurroundingSquares(int x, int y) {

        return false;
    }

    public String announceResults() {
        return "";
    }
}
