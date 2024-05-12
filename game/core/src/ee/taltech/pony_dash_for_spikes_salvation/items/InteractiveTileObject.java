package ee.taltech.pony_dash_for_spikes_salvation.items;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import ee.taltech.pony_dash_for_spikes_salvation.scenes.Hud;
import ee.taltech.pony_dash_for_spikes_salvation.screens.PlayScreen;

public abstract class InteractiveTileObject {
    protected Hud hud;
    protected  Fixture fixture;
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected MapObject object;

    protected InteractiveTileObject(World world, TiledMap map, MapObject object, Hud hud) {
        this.object = object;
        this.world = world;
        this.map = map;
        this.bounds = ((RectangleMapObject)object).getRectangle();
        this.hud = hud;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / PlayScreen.getPPM(), (bounds.getY() + bounds.getHeight() / 2) / PlayScreen.getPPM());

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / PlayScreen.getPPM(), bounds.getHeight() / 2 / PlayScreen.getPPM());
        fdef.shape = shape;
        fixture = body.createFixture(fdef);

    }

    public abstract void onHeadHit();
    public void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }
    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(16);
        return layer.getCell((int)(body.getPosition().x * PlayScreen.getPPM() / 16),
                (int)(body.getPosition().y * PlayScreen.getPPM() / 16));
    }

    public float getCellX() {
        return body.getPosition().x;
    }

    public float getCellY() {
        return body.getPosition().y;
    }

    public Fixture getFixture() {
        return fixture;
    }
}
