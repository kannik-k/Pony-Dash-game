package ee.taltech.pony_dash_for_spikes_salvation;

import Screens.PlayScreen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class pony_dash_for_spikes_salvation_game extends Game {
	public SpriteBatch batch; // holds stuff, for example maps. One is enough.

	private int x = 0, y = 0;

	private Client client;

	@Override
	public void create () {
		client = new Client();
		client.start();
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
		client.sendTCP("Start");
		try {
			client.connect(5000, "localhost", 8080, 8081);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void sendPositionInfoToServer() {
		client.sendUDP(x + "|" + y);
	}

	public void makePlayerMove() {
		batch.draw(PlayScreen.texture, x , y); // draws texture
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			x -= 10;
			sendPositionInfoToServer();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			x += 10;
			sendPositionInfoToServer();
		}
	}

	@Override
	public void render() {
		super.render(); // delegate render to playscreen
	}
	
	@Override
	public void dispose () {
		client.close();
		try {
			client.dispose();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		batch.dispose();
	}
}
