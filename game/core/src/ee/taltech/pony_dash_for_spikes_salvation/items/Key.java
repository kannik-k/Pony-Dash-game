package ee.taltech.pony_dash_for_spikes_salvation.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.scenes.Hud;


public class Key extends InteractiveTileObject {
    public Key(World world, TiledMap map, MapObject object, Hud hud, Main game) {
        super(world, map, object, hud, game);
        fixture.setUserData(this);
        setCategoryFilter(Main.KEY_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Key", "Collision");
        collected();
        game.getManager().get("Game Assets/mixkit-winning-a-coin-video-game-2069.wav", Sound.class).play();
    }

    public void collected() {
        setCategoryFilter(Main.COLLECTED_BIT);
        getCell().setTile(null);
        hud.addKey();
    }
}
