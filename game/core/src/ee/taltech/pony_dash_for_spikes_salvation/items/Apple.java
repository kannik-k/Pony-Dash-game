package ee.taltech.pony_dash_for_spikes_salvation.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.Player;
import ee.taltech.pony_dash_for_spikes_salvation.packets.PacketPowerUpTaken;
import ee.taltech.pony_dash_for_spikes_salvation.scenes.Hud;

import java.time.LocalDateTime;

public class Apple extends InteractiveTileObject {
    private final Player player;

    public Apple(World world, TiledMap map, MapObject object, Hud hud, Player player, Main game) {
        super(world, map, object, hud, game);
        this.player = player;

        fixture.setUserData(this);
        setCategoryFilter(Main.APPLE_BIT);
    }

    @Override
    public String toString() {
        return "Apple at: " + getCellX() + " " + getCellY();
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Apple", "Collision");
        collected();
        player.setGotAppleTime(LocalDateTime.now());
        PacketPowerUpTaken packetPowerUpTaken = new PacketPowerUpTaken();
        packetPowerUpTaken.setX(body.getPosition().x);
        packetPowerUpTaken.setY(body.getPosition().y);
        packetPowerUpTaken.setGameId(player.getGameID());
        game.sendPacketToServer(packetPowerUpTaken);
    }

    public void collected() {
        setCategoryFilter(Main.COLLECTED_BIT);
        getCell().setTile(null);
        game.getManager().get("Game Assets/mixkit-video-game-health-recharge-2837.wav", Sound.class).play(0.1f);
    }
}
