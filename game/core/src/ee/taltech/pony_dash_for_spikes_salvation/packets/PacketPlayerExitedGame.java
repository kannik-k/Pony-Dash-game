package ee.taltech.pony_dash_for_spikes_salvation.packets;

public class PacketPlayerExitedGame {
    private int id;
    private int gameId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
