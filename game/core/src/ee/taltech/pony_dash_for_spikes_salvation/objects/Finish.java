package ee.taltech.pony_dash_for_spikes_salvation.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.Player;
import ee.taltech.pony_dash_for_spikes_salvation.packets.PacketGameOver;
import ee.taltech.pony_dash_for_spikes_salvation.scenes.Hud;
import ee.taltech.pony_dash_for_spikes_salvation.screens.GameOverScreen;

public class Finish extends InteractiveObject {
    private Main main;
    private GameOverScreen gameOverScreen;

    public Finish(World world, TiledMap map, MapObject object, Hud hud, Main main) {
        super(world, map, object, hud);
        this.main = main;
        this.gameOverScreen = new GameOverScreen(main);
        fixture.setUserData(this);
        setCategoryFilter(Main.FINISH_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Finish", "Collision");
        if (hud.isKeyCollected()) {
            Player player = main.getMyPlayer();
            PacketGameOver packet = new PacketGameOver();
            packet.setPlayerId(main.getPlayerId());
            packet.setGameId(player.getGameID());
            packet.setPlayerName(main.getPlayerName());
            main.sendPacketToServer(packet);
            if (main.isSinglePlayer()) {
                gameOverScreen.setWinnerName(main.getPlayerName());
                gameOverScreen.setWinnerId(main.getPlayerId());
                main.setScreen(gameOverScreen);
                main.getManager().get("Game Assets/yay-101soundboards.mp3", Sound.class);
            }
        }
    }
}
