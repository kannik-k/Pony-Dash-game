package ee.taltech.pony_dash_for_spikes_salvation.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import ee.taltech.pony_dash_for_spikes_salvation.screens.PlayScreen;

public class PonySprite extends Sprite {
    private final World world;
    private Body b2body;


    public Body getB2body() {
        return b2body;
    }

    public PonySprite(World world) {
        this.world = world;
        definePony();
    }

    public void definePony() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / PlayScreen.getPPM(), 80 / PlayScreen.getPPM());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / PlayScreen.getPPM());

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
