package ee.taltech.pony_dash_for_spikes_salvation.ai;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class NPC extends Sprite {
    private float x; // Box2D world coordinate
    private float y;
    private int tiledX; // Tiled coordinate in pixels
    private int tiledY;
    private float moveX; // Coordinates (pixels) where the NPC is moving to at the moment
    private float moveY;
    private final int id;
    private long receiveDifference = 0;
    private long lastReceive = 0;
    private static final float PPM = 100f; // pixels per meter

    /**
     * Initiate a new npc sprite.
     * @param id of npc
     * @param tiledX tiled x coordinate of npc in pixels
     * @param tiledY tiled y coordinate of npc in pixels
     * @param sprite of npc
     */
    public NPC(int id, int tiledX, int tiledY, Sprite sprite) {
        super(sprite);
        this.id = id;
        this.tiledX = tiledX;
        x = tiledX / PPM;
        this.tiledY = tiledY;
        y = tiledY / PPM;
        this.moveX = tiledX;
        this.moveY = tiledY;
        setBounds(tiledX, tiledY, 32 / PPM, 32 / PPM);
    }

    /**
     * Update npc.
     * @param dt delta time
     */
    public void update(float dt) {
        double speed = (dt / (receiveDifference)) * 1000;
        if (tiledX > moveX) {
            tiledX -= (int) (16 * speed);
            x = tiledX / PPM;
        }
        if (tiledX < moveX) {
            tiledX += (int) (16 * speed);
            x = tiledX / PPM;
        }
        if (tiledY > moveY) {
            tiledY -= (int) (16 * speed);
            y = tiledY / PPM;
        }
        if (tiledY < moveY) {
            tiledY += (int) (16 * speed);
            y = tiledY / PPM;
        }
        setPosition(x, y);
    }

    public int getId() {
        return id;
    }

    public void setMoveX(float moveX) {
        this.moveX = moveX;
    }

    public void setMoveY(float moveY) {
        this.moveY = moveY;
    }

    public void setReceiveDifference(long receiveDifference) {
        this.receiveDifference = receiveDifference;
    }

    public void setLastReceive(long lastReceive) {
        this.lastReceive = lastReceive;
    }

    public long getLastReceive() {
        return lastReceive;
    }
}
