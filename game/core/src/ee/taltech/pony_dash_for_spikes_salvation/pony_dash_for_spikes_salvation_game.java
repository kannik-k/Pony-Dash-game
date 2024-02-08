package ee.taltech.pony_dash_for_spikes_salvation;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class pony_dash_for_spikes_salvation_game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	private int x = 0, y = 0;

	private Client client;

	@Override
	public void create () {
		client = new Client();
		client.start();
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		client.sendTCP("Start");
		try {
			client.connect(5000, "localhost", 8080, 8081);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void sendPositionInfoToServer() {
		client.sendUDP(x + "|" + y);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			x -= 10;
			sendPositionInfoToServer();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			x += 10;
			sendPositionInfoToServer();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			y += 10;
			sendPositionInfoToServer();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			y -= 10;
			sendPositionInfoToServer();
		}
		batch.begin();
		batch.draw(img, x, y);
		batch.end();
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
		img.dispose();
	}
}
