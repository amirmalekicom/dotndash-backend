import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Playground {
    private int width, height;
    private final List<Player> players;
    private int playerToRoll;
    private boolean[][] horizontalDashBoard;
    private boolean[][] verticalDashBoard;
    private int[][] squareBoard;

    public Playground(List<Player> players) {
        this.players = players;
    }

    public String getPlayerToRollName() {
        return players.get(playerToRoll).getName();
    }

    public int play(String orientation, int x, int y) {
        // convert to zero-based
        x--;
        y--;
        // check dash availability
        orientation = orientation.toLowerCase();
        if ((orientation.equals("h")) && !(x < 0 || x >= width || y < 0 || y > height)) {
            if (horizontalDashBoard[x][y])
                return 2;
            horizontalDashBoard[x][y] = true;
        } else if ((orientation.equals("v")) && !(x < 0 || x > width || y < 0 || y >= height)) {
            if (verticalDashBoard[x][y])
                return 2;
            verticalDashBoard[x][y] = true;
        } else {
            return 1;
        }
        // check for new squares and end game
        int checkResult = checkSquares();
        if (checkResult == -1) {
            return -1;
        }
        if (checkResult > 0) {
            return 3;
        }
        playerToRoll = nextPlayer();
        return 0;
    }

    public void reset() {
        horizontalDashBoard = new boolean[width][height + 1];
        verticalDashBoard = new boolean[width + 1][height];
        squareBoard = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                squareBoard[i][j] = -1;
            }
        }
        playerToRoll = ThreadLocalRandom.current().nextInt(0, players.size());
    }

    public int checkSquares() {
        int finishedSquares = 0;
        int newScoredSquares = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (squareBoard[i][j] == -1) {
                    if (horizontalDashBoard[i][j] && horizontalDashBoard[i][j + 1]
                            && verticalDashBoard[i][j] && verticalDashBoard[i + 1][j]) {
                        finishedSquares++;
                        newScoredSquares++;
                        squareBoard[i][j] = playerToRoll;
                    }
                } else {
                    finishedSquares++;
                }
            }
        }
        if (finishedSquares == width * height) {
            return -1;
        }
        return newScoredSquares;
    }

    public String calculateResults() {
        List<Integer> playersMatchScores = new ArrayList<>(players.size());
        for (int i = 0; i < players.size(); i++) {
            playersMatchScores.add(i, 0);
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int squareOwner = squareBoard[i][j];
                playersMatchScores.set(squareOwner, playersMatchScores.get(squareOwner) + 1);
            }
        }
        //
        int winnerIndex = 0;
        int maxMatchScore = 0;
        for (int i = 0; i < playersMatchScores.size(); i++) {
            if (playersMatchScores.get(i) > maxMatchScore) {
                winnerIndex = i;
                maxMatchScore = playersMatchScores.get(i);
            }
        }
        int numberOfWinners = 0;
        for (Integer playerMatchScore : playersMatchScores) {
            if (playerMatchScore == maxMatchScore) {
                numberOfWinners++;
            }
        }
        if (numberOfWinners == 1) {
            Player winner = players.get(winnerIndex);
            winner.setScore(winner.getScore() + 1);
            return String.format("%s won!", winner.getName());
        } else {
            return "Draw :(, nobody won...";
        }
    }

    private int nextPlayer() {
        if (playerToRoll + 1 == players.size()) {
            return 0;
        }
        return playerToRoll + 1;
    }

    public String getVisualRepresentation() {
        StringBuilder board = new StringBuilder();
        for (int j = -1; j < height; j++) {
            if (j == -1) {
                board.append(".");
            } else {
                if (verticalDashBoard[0][j])
                    board.append("!");
                else
                    board.append(".");
            }
            for (int i = 0; i < width; i++) {
                if (j == -1) {
                    if (horizontalDashBoard[i][j + 1])
                        board.append("_");
                    else
                        board.append(" ");
                    board.append(".");
                } else {
                    if (horizontalDashBoard[i][j + 1]) {
                        if (squareBoard[i][j] == -1)
                            board.append("_");
                        else
                            board.append("≣");
                    } else {
                        board.append(" ");
                    }
                    if (verticalDashBoard[i + 1][j])
                        board.append("!");
                    else
                        board.append(".");
                }
            }
            board.append("\n");
        }
        return board.toString();
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public String getStats() {
        ArrayList<Player> sortedPlayers = new ArrayList<>(players);
        sortedPlayers.sort(Comparator.comparing(Player::getScore).reversed());

        StringBuilder stats = new StringBuilder();
        for (Player playerToShow : sortedPlayers) {
            stats.append(String.format("%s: %d\n", playerToShow.getName(), playerToShow.getScore()));
        }
        return stats.toString();
    }
}
