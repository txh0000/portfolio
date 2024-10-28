package gameObject.player;

public abstract class Player {

    private int id;
    private String playerType;

    public Player(int id, String playerType) {
        this.id = id;
        this.playerType = playerType;
    }

    public int getId() {
        return id;
    }

    public String getPlayerType() {
        return this.playerType;
    }
}
