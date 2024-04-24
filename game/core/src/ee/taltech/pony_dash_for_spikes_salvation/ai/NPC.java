package ee.taltech.pony_dash_for_spikes_salvation.ai;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import static ee.taltech.pony_dash_for_spikes_salvation.screens.PlayScreen.getPPM;

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
    private Body b2body;
    private final World world;
    private static final float PPM = 100f; // pixels per meter

    /**
     * Initiate a new npc sprite.
     * @param id of npc
     * @param tiledX tiled x coordinate of npc in pixels
     * @param tiledY tiled y coordinate of npc in pixels
     * @param sprite of npc
     * @param world the npc belongs in
     */
    public NPC(int id, int tiledX, int tiledY, Sprite sprite, World world) {
        super(sprite);
        this.id = id;
        this.tiledX = tiledX;
        x = tiledX / PPM;
        this.tiledY = tiledY;
        y = tiledY / PPM;
        this.world = world;
        this.moveX = tiledX;
        this.moveY = tiledY;
        // defineNPC();
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
        System.out.println(" updated spot:" + tiledX / 16 + " " + tiledY / 16);
        // b2body.getPosition().set(x, y);
        // setRegion(getFrame(dt)); // Will use later for animation maybe
    }

    /**
     * Define npc.
     */
    public void defineNPC() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(64 / getPPM(), 420 / getPPM());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(14 / getPPM(), 14 / getPPM());

        fdef.shape = shape;
        b2body.createFixture(fdef);
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
