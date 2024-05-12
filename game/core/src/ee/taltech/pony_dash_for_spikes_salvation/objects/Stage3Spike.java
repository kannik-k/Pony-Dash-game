package ee.taltech.pony_dash_for_spikes_salvation.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.Player;
import ee.taltech.pony_dash_for_spikes_salvation.scenes.Hud;

public class Stage3Spike extends InteractiveObject {
    private final Player player;

    public Stage3Spike(World world, TiledMap map, MapObject object, Hud hud, Player player) {
        super(world, map, object, hud);
        this.player = player;

        fixture.setUserData(this);
        setCategoryFilter(Main.SPIKE_3_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Spike3", "Collision");
        player.setTeleporting3(true);
    }
}