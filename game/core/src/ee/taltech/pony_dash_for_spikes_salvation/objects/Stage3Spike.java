package ee.taltech.pony_dash_for_spikes_salvation.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.items.InteractiveTileObject;
import ee.taltech.pony_dash_for_spikes_salvation.scenes.Hud;

public class Stage3Spike extends InteractiveTileObject {
    public Stage3Spike(World world, TiledMap map, MapObject object, Hud hud, Main game) {
        super(world, map, object, hud, game);
        fixture.setUserData(this);
        setCategoryFilter(Main.SPIKE_3_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Spike3", "Collision");
        game.getManager().get("Game Assets/mixkit-player-losing-or-failing-2042.wav", Sound.class).play(0.1f);
        game.getMyPlayer().setTeleporting3(true);
    }
}