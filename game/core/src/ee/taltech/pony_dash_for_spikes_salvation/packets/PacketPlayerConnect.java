package ee.taltech.pony_dash_for_spikes_salvation.packets;

public class PacketPlayerConnect extends Packet {
    private String playerName;
    private int playerID;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
}
