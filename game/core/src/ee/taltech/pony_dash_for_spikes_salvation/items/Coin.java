package ee.taltech.pony_dash_for_spikes_salvation.items;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.screens.PlayScreen;

public class Coin extends InteractiveTileObject {
    private static TiledMapTileSet set;
    public Coin(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        set = map.getTileSets().getTileSet("MonedaD.tsx");
        tile = set.getTile(0);
        fixture.setUserData(this);
        setCategoryFilter(Main.COIN_BIT);
    }

    public void collected() {
        setCategoryFilter(Main.COLLECTED_BIT);
        getCell().setTile(null);
    }
    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(16);
        return layer.getCell((int)(body.getPosition().x * PlayScreen.getPPM() / 16),
                (int)(body.getPosition().y * PlayScreen.getPPM() / 16));
    }
}
