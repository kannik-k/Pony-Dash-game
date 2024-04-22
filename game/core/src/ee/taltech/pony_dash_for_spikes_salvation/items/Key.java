package ee.taltech.pony_dash_for_spikes_salvation.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.World;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.scenes.Hud;
import ee.taltech.pony_dash_for_spikes_salvation.screens.PlayScreen;

public class Key extends InteractiveTileObject {
    public Key(World world, TiledMap map, MapObject object, Hud hud) {
        super(world, map, object, hud);
        fixture.setUserData(this);
        setCategoryFilter(Main.KEY_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Key", "Collision");
        collected();
    }

    public void collected() {
        setCategoryFilter(Main.COLLECTED_BIT);
        getCell().setTile(null);
    }
}
