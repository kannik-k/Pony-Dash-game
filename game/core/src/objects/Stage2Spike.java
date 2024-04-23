package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.items.InteractiveTileObject;
import ee.taltech.pony_dash_for_spikes_salvation.scenes.Hud;

public class Stage2Spike extends InteractiveTileObject {

    public Stage2Spike(World world, TiledMap map, MapObject object, Hud hud) {
        super(world, map, object, hud);
        fixture.setUserData(this);
        setCategoryFilter(Main.SPIKE_2_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Spike2", "Collision");

    }
}