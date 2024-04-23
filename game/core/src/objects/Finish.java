package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.Player;
import ee.taltech.pony_dash_for_spikes_salvation.packets.PacketGameOver;
import ee.taltech.pony_dash_for_spikes_salvation.scenes.Hud;

public class Finish extends InteractiveObject {
    private Main main;

    public Finish(World world, TiledMap map, MapObject object, Hud hud, Main main) {
        super(world, map, object, hud);
        this.main = main;
        fixture.setUserData(this);
        setCategoryFilter(Main.FINISH_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Finish", "Collision");
        System.out.println(hud.isKeyCollected());
        if (hud.isKeyCollected()) {
            Player player = main.getMyPlayer();
            PacketGameOver packet = new PacketGameOver();
            packet.setPlayerId(main.getPlayerId());
            packet.setGameId(player.getGameID());
            System.out.println("Packet for server " + packet);
            main.sendPacketToServer(packet);
        }
    }
}
