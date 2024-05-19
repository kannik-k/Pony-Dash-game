package ee.taltech.pony_dash_for_spikes_salvation.sprites;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.Player;
import ee.taltech.pony_dash_for_spikes_salvation.screens.PlayScreen;

import java.util.Arrays;
import java.util.List;

import static ee.taltech.pony_dash_for_spikes_salvation.screens.PlayScreen.getPPM;

public class PonySprite extends Sprite {

    private enum State {FALLING, JUMPING, STANDING, RUN}
    private State currentState;
    private State previousState;
    private final World world;
    private Body b2body;

    // Animation
    private final TextureRegion ponyStill;
    private final Animation<TextureRegion> ponyRun;
    private final TextureRegion ponyJump;
    private final TextureRegion ponyFall;
    private boolean runningRight;
    private float stateTimer;
    private Player player;
    private int id;
    List<Integer> animationInformation = Arrays.asList(2, 546, 560, 418, 2, 15, 18, 0, 0);

    /**
     * Gets b 2 body.
     *
     * @return the b 2 body
     */
    public Body getB2body() {
        return b2body;
    }

    /**
     * Instantiates a new Pony sprite.
     *
     * @param world  the world
     * @param screen the screen
     * @param player the player
     * @param id the id of the sprite
     */
    public PonySprite(World world, PlayScreen screen, Player player, int id) {
        super(screen.getAtlas().findRegion("applejack"));
        this.world = world;
        this.player = player;
        this.id = id;
        currentState = State.STANDING;
        previousState = State.STANDING;
        this.runningRight = true;
        this.stateTimer = 0;
        Array<TextureRegion> frames = new Array<>();

        setAnimationInformation();

        // Pony run animation
        for (int i = animationInformation.get(5); i < animationInformation.get(6); i++) {
            frames.add(new TextureRegion(getTexture(), i * 32 + animationInformation.get(4),
                    32 * animationInformation.get(0) + animationInformation.get(7), 32, 32));
        }
        ponyRun = new Animation<>(0.18f, frames);

        frames.clear();

        // Pony jump animation
        ponyJump = new TextureRegion(getTexture(), animationInformation.get(1) + animationInformation.get(8),
                32 * animationInformation.get(0) + animationInformation.get(7), 32, 32);

        // Pony fall animation
        ponyFall = new TextureRegion(getTexture(), animationInformation.get(2) + animationInformation.get(4),
                32 * animationInformation.get(0) + animationInformation.get(7), 32, 32);

        // Pony standing still
        ponyStill = new TextureRegion(getTexture(), animationInformation.get(3),
                32 * animationInformation.get(0) + animationInformation.get(7), 32, 32);

        definePony();

        setBounds(418, 65, 32 / getPPM(), 32 / getPPM());
        setRegion(ponyStill);
    }

    /**
     * Sets animation information based on the sprite ID.
     */
    private void setAnimationInformation() {
        if (id == 1) {
            animationInformation = Arrays.asList(2, 482, 512, 418, 2, 15, 18, 0, 0);
        } else if (id == 5) {
            animationInformation = Arrays.asList(10, 480, 512, 419, 3, 15, 18, 3, 3);
        } else if (id == 4) {
            animationInformation = Arrays.asList(18, 480, 512, 419, 3, 15, 18, 4, 3);
        } else if (id == 2) {
            animationInformation = Arrays.asList(2, 96, 128, 32, 2, 2, 5, 0, 0);
        } else if (id == 3) {
            animationInformation = Arrays.asList(18, 96, 128, 32, 0, 2, 5, 4, 3);
        } else if (id == 6) {
            animationInformation = Arrays.asList(10, 96, 128, 33, 2, 2, 5, 3, 1);
        }
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets current state.
     *
     * @return the current state
     */
    public String getCurrentState() {
        return currentState.name().toLowerCase();
    }

    /**
     * Update player.
     *
     * @param dt the delta time
     * @param spriteId the sprite ID
     * @param state the state as a string
     */
    public void update(float dt, int spriteId, String state) {
        float x = player.getX();
        float y = player.getY();
        id = spriteId;
        setState(State.valueOf(state.toUpperCase()));
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
        b2body.setTransform(x / getPPM(), y / getPPM(), 0);
        setRegion(getFrame(dt));
        player.setState(getCurrentState());
    }

    /**
     * Gets frame.
     *
     * @param dt the delta time
     * @return the frame
     */
    public TextureRegion getFrame(float dt) {
        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = ponyJump;
                break;
            case FALLING:
                region = ponyFall;
                break;
            case RUN:
                region = ponyRun.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            default:
                region = ponyStill;
                break;
        }

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public State getState() {
        if (b2body.getLinearVelocity().y > 0) {
            return State.JUMPING;
        } else if (b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        } else if (b2body.getLinearVelocity().x != 0) {
            return State.RUN;
        } else {
            return State.STANDING;
        }
    }

    /**
     * Define pony.
     */
    public void definePony() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(64 / getPPM(), 420 / getPPM());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(14 / getPPM(), 14 / getPPM());
        fdef.filter.categoryBits = Main.CHAR_BIT;
        fdef.filter.maskBits = Main.DEFAULT_BIT | Main.STAGE_BLOCK_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-16 / getPPM(), 16 / getPPM()), new Vector2(16 / getPPM(), 16 / getPPM()));
        fdef.shape = head;
        fdef.isSensor = true;
        fdef.filter.maskBits = Main.COIN_BIT | Main.KEY_BIT | Main.FINISH_BIT | Main.SPIKE_2_BIT | Main.SPIKE_3_BIT | Main.STAGE_BLOCK_BIT | Main.CHERRY_BIT | Main.APPLE_BIT;
        b2body.createFixture(fdef).setUserData("pony");

        head.set(new Vector2(-16 / getPPM(), -16 / getPPM()), new Vector2(16 / getPPM(), -16 / getPPM()));
        fdef.shape = head;
        b2body.createFixture(fdef).setUserData("pony");

        head.set(new Vector2(-16 / getPPM(), 16 / getPPM()), new Vector2(-16 / getPPM(), -16 / getPPM()));
        fdef.shape = head;
        b2body.createFixture(fdef).setUserData("pony");

        head.set(new Vector2(16 / getPPM(), -16 / getPPM()), new Vector2(16 / getPPM(), 16 / getPPM()));
        fdef.shape = head;
        b2body.createFixture(fdef).setUserData("pony");
    }

    /**
     * Sets the current state of the sprite.
     *
     * @param state the new state
     */
    public void setState(State state) {
        this.currentState = state;
    }
}
