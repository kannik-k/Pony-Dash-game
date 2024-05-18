package ee.taltech.pony_dash_for_spikes_salvation.tools;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.items.*;
import ee.taltech.pony_dash_for_spikes_salvation.objects.*;
import ee.taltech.pony_dash_for_spikes_salvation.scenes.Hud;
import ee.taltech.pony_dash_for_spikes_salvation.screens.PlayScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map, Main game, Hud hud, Map<List<Integer>, InteractiveTileObject> powerUps) {
        // Ajutine, tuleb hiljem ümber tõsta
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // Ground, temporary
        for (RectangleMapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = (object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / PlayScreen.getPPM(), (rectangle.getY() + rectangle.getHeight() / 2) / PlayScreen.getPPM());

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth() / 2 / PlayScreen.getPPM(), rectangle.getHeight() / 2 / PlayScreen.getPPM());
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        for (RectangleMapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = (object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / PlayScreen.getPPM(), (rectangle.getY() + rectangle.getHeight() / 2) / PlayScreen.getPPM());

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth() / 2 / PlayScreen.getPPM(), rectangle.getHeight() / 2 / PlayScreen.getPPM());
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        // Coin
        for(RectangleMapObject object: map.getLayers().get(15).getObjects().getByType(RectangleMapObject.class)) {
            new Coin(world, map, object, hud, game);
        }
        //Key
        for(RectangleMapObject object: map.getLayers().get(17).getObjects().getByType(RectangleMapObject.class)) {
            new Key(world, map, object, hud, game);
        }
        //Spikes stage 2
        for(RectangleMapObject object: map.getLayers().get(13).getObjects().getByType(RectangleMapObject.class)) {
            new Stage2Spike(world, map, object, hud, game);
        }
        //Spikes Stage 3
        for(RectangleMapObject object: map.getLayers().get(14).getObjects().getByType(RectangleMapObject.class)) {
            new Stage3Spike(world, map, object, hud, game);
        }
        //Finish
        for(RectangleMapObject object: map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
            new Finish(world, map, object, hud, game);
        }
        //Stage2
        for(RectangleMapObject object: map.getLayers().get(18).getObjects().getByType(RectangleMapObject.class)) {
            new Stage2(world, map, object, hud, game);
        }
        //Stage3
        for(RectangleMapObject object: map.getLayers().get(19).getObjects().getByType(RectangleMapObject.class)) {
            new Stage3(world, map, object, hud, game);
        }
        //Cherries
        for(RectangleMapObject object: map.getLayers().get(20).getObjects().getByType(RectangleMapObject.class)) {
            Cherry cherryObject = new Cherry(world, map, object, hud, game.getMyPlayer(), game);
            List<Integer> coordinates = new ArrayList<>();
            coordinates.add(Math.round(cherryObject.getCellX()));
            coordinates.add(Math.round(cherryObject.getCellY()));
            powerUps.put(coordinates, cherryObject);
        }
        //Apples
        for(RectangleMapObject object: map.getLayers().get(21).getObjects().getByType(RectangleMapObject.class)) {
            Apple appleObject = new Apple(world, map, object, hud, game.getMyPlayer(), game);
            List<Integer> coordinates = new ArrayList<>();
            coordinates.add(Math.round(appleObject.getCellX()));
            coordinates.add(Math.round(appleObject.getCellY()));
            powerUps.put(coordinates, appleObject);
        }
    }
}
