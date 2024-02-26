package ee.taltech.pony_dash_for_spikes_salvation;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import ee.taltech.pony_dash_for_spikes_salvation.exceptions.ConnectionException;
import ee.taltech.pony_dash_for_spikes_salvation.packets.PacketPlayerConnect;
import ee.taltech.pony_dash_for_spikes_salvation.packets.PacketSendCoordinates;
import ee.taltech.pony_dash_for_spikes_salvation.screens.PlayScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main extends Game {
	private SpriteBatch batch; // holds stuff, for example maps. One is enough.
	private int x = 0;
	private int y = 0;
	private Client client;
	private Map<Integer, Player> players = new HashMap<>();

	public SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public void create () {
		client = new Client();
		client.start();
		Network.register(client);
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
		try {
			client.connect(5000, "localhost", 8080, 8081);
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage());
		}
		PacketPlayerConnect packetPlayerConnect = new PacketPlayerConnect();
		packetPlayerConnect.setPlayerName("player");
		client.sendTCP(packetPlayerConnect); // Send server info that client has connected
		client.addListener(new Listener.ThreadedListener(new Listener() {
			@Override
			public void received(Connection connection, Object object) {
				if (object instanceof PacketPlayerConnect) {
					Player player = new Player(((PacketPlayerConnect) object).getPlayerName());
					players.put(((PacketPlayerConnect) object).getPlayerID(), player);
				}
				if (object instanceof PacketSendCoordinates) {
					Player player = players.get(((PacketSendCoordinates) object).getPlayerID());
					player.setX(((PacketSendCoordinates) object).getX());
					player.setY(((PacketSendCoordinates) object).getY());
				}
			}
		}));
	}

	public void sendPositionInfoToServer() {
		PacketSendCoordinates packetSendCoordinates = new PacketSendCoordinates();
		packetSendCoordinates.setX(x);
		packetSendCoordinates.setY(y);
		packetSendCoordinates.setPlayerID(client.getID());
		client.sendUDP(packetSendCoordinates);
	}

	public void makePlayerMove() {
		batch.draw(PlayScreen.getTexture(), x , y); // draws texture
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			x -= 10;
			sendPositionInfoToServer();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			x += 10;
			sendPositionInfoToServer();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			y += 10;
			sendPositionInfoToServer();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			y -= 10;
			sendPositionInfoToServer();
		}
	}

	/**
	 * Draw all player movements.
	 * Ajutiselt välja kommenteeritud (katsetame collison'i).
	 */
	public void makeAllPlayersMove() {
		for (Map.Entry<Integer, Player> set : players.entrySet()) {
			batch.draw(PlayScreen.getTexture(), set.getValue().getX(), set.getValue().getY());
		}
	}
	
	@Override
	public void dispose () {
		client.close();
		try {
			client.dispose();
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage());
		}
		batch.dispose();
	}
}
