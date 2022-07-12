import java.util.List;

public class Playground {
    private int width, height;
    private List<Player> players;  // TODO: randomize turns
    private Player playerToRoll;

    public Playground(int width, int height, List<Player> players) {
        this.width = width;
        this.height = height;
        this.players = players;
    }

    public String getPlayerToRollName() {
        return playerToRoll.getName();
    }

    public int play() {
        return 0;
    }

    public void reset() {

    }

    public String announceResults() {
        return "";
    }
}
