package ee.taltech.pony_dash_for_spikes_salvation.lobby;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    private int lobbyId;
    private static int lastLobbyId = 0;
    private static final int MAX_PLAYERS = 6;

    private final List<String> players = new ArrayList<>();

    public Lobby() {
        this.lobbyId = ++lastLobbyId;
    }

    public void incrementLobbyId() {
        this.lobbyId++;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void clearPeers() {
        this.players.clear();
    }

    public boolean addPlayer(String player) {
        if (players.size() < MAX_PLAYERS) {
            players.add(player);
            return true;
        }
        return false;
    }

    public void removePlayer(String player) {
        this.players.remove(player);
    }
}
