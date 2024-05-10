package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.objects.InteractiveObject;
import ee.taltech.pony_dash_for_spikes_salvation.scenes.Hud;

public class Stage3 extends InteractiveObject {
    private Main game;
    public Stage3(World world, TiledMap map, MapObject object, Hud hud, Main game) {
        super(world, map, object,hud);
        this.game = game;
        fixture.setUserData(this);
        setCategoryFilter(Main.STAGE_BLOCK_BIT);
    }

    @Override
    public void onHeadHit() {
        if (hud.getCoins() >= 35) {
            Gdx.app.log("Stage3", "New area unlocked!");
            game.getManager().get("Game Assets/mixkit-game-bonus-reached-2065.wav", Sound.class).play(0.1f);
            setCategoryFilter(Main.COLLECTED_BIT);
        } else {
            Gdx.app.log("Stage3", "Not enough coins");
            game.getManager().get("Game Assets/mixkit-small-hit-in-a-game-2072.wav", Sound.class).play(0.1f);
        }
    }
}
