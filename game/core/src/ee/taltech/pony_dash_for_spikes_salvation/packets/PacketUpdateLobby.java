package ee.taltech.pony_dash_for_spikes_salvation.packets;

public class PacketUpdateLobby {
    private int lobbySize;
    final int gameId = 0;

    public int getLobbySize() {
        return lobbySize;
    }

    public void setLobbySize(int lobbySize) {
        this.lobbySize = lobbySize;
    }

    public int getGameId() {
        return gameId;
    }
}