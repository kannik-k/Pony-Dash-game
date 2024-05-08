package ee.taltech.pony_dash_for_spikes_salvation.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.Player;
import ee.taltech.pony_dash_for_spikes_salvation.scenes.Hud;

import java.time.LocalDateTime;

public class Cherry extends InteractiveTileObject {
    private Player player;

    public Cherry(World world, TiledMap map, MapObject object, Hud hud, Player player) {
        super(world, map, object, hud);
        this.player = player;

        fixture.setUserData(this);
        setCategoryFilter(Main.CHERRY_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Cherry", "Collision");
        collected();
        player.setGotCherryTime(LocalDateTime.now());
    }

    public void collected() {
        setCategoryFilter(Main.COLLECTED_BIT);
        getCell().setTile(null);
    }
}
