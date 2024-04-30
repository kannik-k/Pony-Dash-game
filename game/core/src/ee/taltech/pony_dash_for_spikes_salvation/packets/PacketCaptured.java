package ee.taltech.pony_dash_for_spikes_salvation.packets;

public class PacketCaptured {
    private String time;
    private int playerId;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
