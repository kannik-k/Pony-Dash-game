package ee.taltech.pony_dash_for_spikes_salvation.packets;

import java.util.ArrayList;
import java.util.List;

public class PacketLobby extends Packet{
    private int lobbyID;
    private List<String> players = new ArrayList<>();

    public PacketLobby() {
    }

    public PacketLobby(int lobbyId, List<String> players) {
        this.lobbyID = lobbyId;
        this.players = players;
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public void setLobbyID(int lobbyID) {
        this.lobbyID = lobbyID;
    }

    public List<String> getPlayers() {
        return players;
    }
}
