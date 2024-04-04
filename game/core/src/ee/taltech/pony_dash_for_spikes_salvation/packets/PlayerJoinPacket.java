package ee.taltech.pony_dash_for_spikes_salvation.packets;

public class PlayerJoinPacket {
    private float x;
    private float y;
    private int id;
    private String userName;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }
}
