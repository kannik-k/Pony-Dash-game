package ee.taltech.pony_dash_for_spikes_salvation.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.Player;
import ee.taltech.pony_dash_for_spikes_salvation.scenes.Hud;

import java.time.LocalDateTime;

public class Apple extends InteractiveTileObject {
    private Player player;

    public Apple(World world, TiledMap map, MapObject object, Hud hud, Player player) {
        super(world, map, object, hud);
        this.player = player;

        fixture.setUserData(this);
        setCategoryFilter(Main.APPLE_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Apple", "Collision");
        collected();
        player.setGotAppleTime(LocalDateTime.now());
    }

    public void collected() {
        setCategoryFilter(Main.COLLECTED_BIT);
        getCell().setTile(null);
    }
}
