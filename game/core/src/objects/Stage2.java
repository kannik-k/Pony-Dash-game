package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.scenes.Hud;

public class Stage2 extends InteractiveObject {
    public Stage2(World world, TiledMap map, MapObject object, Hud hud) {
        super(world, map, object,hud);
        fixture.setUserData(this);
        setCategoryFilter(Main.STAGE_BLOCK_BIT);
    }

    @Override
    public void onHeadHit() {
        if (hud.getCoins() >= 15) {
            Gdx.app.log("Stage2", "New area unlocked!");
            setCategoryFilter(Main.COLLECTED_BIT);
            hud.setMapStage(2);
        } else {
            Gdx.app.log("Stage2", "Not enough coins");
        }
    }
}
