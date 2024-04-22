package ee.taltech.pony_dash_for_spikes_salvation.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.Player;
import ee.taltech.pony_dash_for_spikes_salvation.packets.Game;
import ee.taltech.pony_dash_for_spikes_salvation.scenes.Hud;
import ee.taltech.pony_dash_for_spikes_salvation.screens.PlayScreen;

public class Coin extends InteractiveTileObject {

    public Coin(World world, TiledMap map, MapObject object, Hud hud) {
        super(world, map, object, hud);
        fixture.setUserData(this);
        setCategoryFilter(Main.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin", "Collision");
        collected();
        hud.addScore(1);
    }

    public void collected() {
        setCategoryFilter(Main.COLLECTED_BIT);
        getCell().setTile(null);

    }
}
