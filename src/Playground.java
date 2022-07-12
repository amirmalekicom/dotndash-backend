import java.util.List;

public class Playground {
    private int width, height;
    private List<Player> players;
    private List<Integer> playersIngameScore;
    private int playerToRoll;
    private boolean[][] dashBoard;
    private int[][] squareBoard;

    public Playground(int width, int height, List<Player> players) {
        this.width = width;
        this.height = height;
        this.players = players;
    }

    public String getPlayerToRollName() {
        return players.get(playerToRoll).getName();
    }

    public int play(int x, int y) {
        // convert to zero-based
        x--;
        y--;
        if (x < 1 || x >= width || y < 1 || y >= height) {
            return 1;
        }
        if (dashBoard[x][y]) {
            return 2;
        }
        dashBoard[x][y] = true;
        if (checkSquares() > 0) {
            return 3;
        }
        if (checkSquares() < 0) {
            return -1;
        }
        playerToRoll = nextPlayer();
        return 0;
    }

    public void reset() {
        dashBoard = new boolean[width][height];
        squareBoard = new int[width - 1][height - 1];
        for (int i = 0; i < players.size(); i++) {
            playersIngameScore.set(i, 0);
        }
        playerToRoll = 0;  // TODO: randomize turns
    }

    public int checkSquares() {
        boolean gameFinished = true;
        boolean scoredSomething = false;
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height - 1; j++) {
                if (squareBoard[i][j] == -1) {
                    gameFinished = false;
                    if (dashBoard[i][j] && dashBoard[i + 1][j] && dashBoard[i][j + 1] && dashBoard[i + 1][j + 1]) {
                        scoredSomething = true;
                        squareBoard[i][j] = playerToRoll;
                    }
                }
            }
        }
        if (gameFinished) {  // TODO: bug: game is finished but not showing scoring sth
            return -1;
        }
        if (scoredSomething) {
            return 1;
        }
        return 0;
    }

    public String announceResults() {
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height - 1; j++) {
                int squareOwner = squareBoard[i][j];
                playersIngameScore.set(squareOwner, playersIngameScore.get(squareOwner) + 1);
            }
        }
        //
        int winnerIndex = 0;
        int maxIngameScore = 0;
        for (int i = 0; i < playersIngameScore.size(); i++) {
            if (playersIngameScore.get(i) > maxIngameScore) {
                winnerIndex = i;
                maxIngameScore = playersIngameScore.get(i);
            }
        }
        int numberOfWinners = 0;
        for (int i = 0; i < playersIngameScore.size(); i++) {
            if (playersIngameScore.get(i) == maxIngameScore) {
                numberOfWinners++;
            }
        }
        if (numberOfWinners == 1) {
            Player winner = players.get(winnerIndex);
            winner.setScore(winner.getScore() + 1);
            return String.format("%s won!", winnerIndex);
        } else {
            return "The game led to a draw... :(";
        }
    }

    private int nextPlayer() {
        if (playerToRoll + 1 == players.size()) {
            return 0;
        }
        return playerToRoll + 1;
    }
}
