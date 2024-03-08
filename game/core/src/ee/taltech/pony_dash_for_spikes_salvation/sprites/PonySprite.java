package ee.taltech.pony_dash_for_spikes_salvation.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import ee.taltech.pony_dash_for_spikes_salvation.Player;
import ee.taltech.pony_dash_for_spikes_salvation.screens.PlayScreen;

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
     */
    public PonySprite(World world, PlayScreen screen, Player player) {
        super(screen.getAtlas().findRegion("twilight"));
        this.world = world;
        this.player = player;
        currentState = State.STANDING;
        previousState = State.STANDING;
        this.runningRight = true;
        this.stateTimer = 0;

        Array<TextureRegion> frames = new Array<>();

        // Pony run animation
        for (int i = 15; i < 18; i++) {
            frames.add(new TextureRegion(getTexture(), i * 32 + 2, 64, 32, 32));
        }
        ponyRun = new Animation<>(0.09f, frames);

        frames.clear();

        // Pony jump animation
        ponyJump = new TextureRegion(getTexture(), 546, 64, 32, 32);

        // Pony fall animation

        ponyFall = new TextureRegion(getTexture(), 512, 64, 32, 32);

        // Pony standing still
        ponyStill = new TextureRegion(getTexture(), 418, 64, 32, 32);

        definePony();

        setBounds(418, 65, 32 / getPPM(), 32 / getPPM());
        setRegion(ponyStill);
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
     * Get state timer float.
     *
     * @return the float
     */
    public float getStateTimer(){
        return stateTimer;
    }

    /**
     * Gets current state.
     *
     * @return the current state
     */
    public String getCurrentState() {
        if (currentState == State.FALLING) {
            return "falling";
        }
        if (currentState == State.RUN) {
            return "run";
        }
        if (currentState == State.STANDING) {
            return "standing";
        }
        if (currentState == State.JUMPING) {
            return "jumping";
        }
        return null;
    }

    /**
     * Update player.
     *
     * @param dt the dt
     */
    public void update(float dt) {
        float x = player.getX();
        float y = player.getY();
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
        b2body.getPosition().set(x, y);
        setRegion(getFrame(dt));
    }

    /**
     * Gets frame.
     *
     * @param dt the dt
     * @return the frame
     */
    public TextureRegion getFrame(float dt) {
        currentState = getState();

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
        bdef.position.set(32 / getPPM(), 420 / getPPM());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(14 / getPPM());

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
