package ee.taltech.pony_dash_for_spikes_salvation;

import com.badlogic.gdx.math.Vector2;
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
import ee.taltech.pony_dash_for_spikes_salvation.sprites.PonySprite;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main extends Game {
	private SpriteBatch batch; // holds stuff, for example maps. One is enough.
	private Client client;
	private Map<Integer, Player> players = new HashMap<>();
	private Player myPlayer;

	public SpriteBatch getBatch() {
		return batch;
	}

	public Client getClient() {
		return client;
	}

	public Map<Integer, Player> getPlayers() {
		return players;
	}

	@Override
	public void create () {
		client = new Client();
		client.start();
		Network.register(client);
		batch = new SpriteBatch();
		myPlayer = new Player("player");
		PlayScreen playScreen = new PlayScreen(this);
		setScreen(playScreen);
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
					if (((PacketPlayerConnect) object).getPlayerID() == connection.getID()) {
						players.put(connection.getID(), myPlayer);
					} else {
						Player player = new Player(((PacketPlayerConnect) object).getPlayerName());
						players.put(((PacketPlayerConnect) object).getPlayerID(), player);
						playScreen.createNewSprite(player);
						System.out.println("NEW PLAYER JOINED");
					}
				}
				if (object instanceof PacketSendCoordinates) {
					Player player = players.get(((PacketSendCoordinates) object).getPlayerID());
					player.setX(((PacketSendCoordinates) object).getX());
					player.setY(((PacketSendCoordinates) object).getY());
				}
			}
		}));
	}

	public Player getMyPlayer() {
		return myPlayer;
	}

	public void sendPositionInfoToServer() {
		PacketSendCoordinates packetSendCoordinates = new PacketSendCoordinates();
		packetSendCoordinates.setX(myPlayer.getSprite().getB2body().getPosition().x);
		packetSendCoordinates.setY(myPlayer.getSprite().getB2body().getPosition().y);
		packetSendCoordinates.setPlayerID(client.getID());
		client.sendUDP(packetSendCoordinates);
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
