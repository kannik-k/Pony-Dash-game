package ee.taltech.pony_dash_for_spikes_salvation.tools;

import com.badlogic.gdx.physics.box2d.*;
import ee.taltech.pony_dash_for_spikes_salvation.items.InteractiveTileObject;
import objects.InteractiveObject;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if ((fixA.getUserData() == "pony" || fixB.getUserData() =="pony") && fixA.getUserData() != fixB.getUserData()) {
            Fixture pony = fixA.getUserData() == "pony" ? fixA : fixB;
            Fixture object = pony == fixA ? fixB : fixA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).onHeadHit();

            }
            else if (object.getUserData() != null && InteractiveObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveObject) object.getUserData()).onHeadHit();

            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
