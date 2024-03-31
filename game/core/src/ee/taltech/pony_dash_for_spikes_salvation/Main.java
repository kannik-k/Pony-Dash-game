package ee.taltech.pony_dash_for_spikes_salvation;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import ee.taltech.pony_dash_for_spikes_salvation.exceptions.ConnectionException;
import ee.taltech.pony_dash_for_spikes_salvation.packets.PacketPlayerConnect;
import ee.taltech.pony_dash_for_spikes_salvation.packets.PacketSendCoordinates;
import ee.taltech.pony_dash_for_spikes_salvation.screens.MenuScreen;
import ee.taltech.pony_dash_for_spikes_salvation.screens.PlayScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main extends Game {
	private SpriteBatch batch; // holds stuff, for example maps. One is enough.
	private BitmapFont font;
	private Client client;
	private Map<Integer, Player> players = new HashMap<>();
	private Player myPlayer;
	private int playerSpriteId;

	public SpriteBatch getBatch() {
		return batch;
	}

	public BitmapFont getFont() {
		return font;
	}

	public Client getClient() {
		return client;
	}

	public Map<Integer, Player> getPlayers() {
		return players;
	}

	/**
	 * Create a new client and player, create listener.
	 * <p>
	 * 	First a new client is created and registered to the network.
	 * 	Secondly a new batch, player and play-screen are created.
	 * 	Next the client is connected to a port, alternatively a ConnectionException is thrown if the connection fails.
	 * 	A new PacketPlayerConnect packet is created that is then sent to the server.
	 * 	A listener is added.
	 * </p>
	 */
	@Override
	public void create () {
		client = new Client();
		client.start();
		Network.register(client);
		batch = new SpriteBatch();
		myPlayer = new Player("player");
		PlayScreen playScreen = new PlayScreen(this);
		MenuScreen menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
		try {
			// client.connect(5000, "localhost", 8080, 8081); // Use this to play on local host
			client.connect(5000, "193.40.255.33", 8080, 8081); // Use this to play on the school server
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage());
		}
		PacketPlayerConnect packetPlayerConnect = new PacketPlayerConnect();
		packetPlayerConnect.setPlayerName("player");
		client.sendTCP(packetPlayerConnect); // Send server info that client has connected
		client.addListener(new Listener.ThreadedListener(new Listener() {
			/**
			 * Create listener for different packets sent to the client.
			 * <p>
			 *     There are two kinds of packets that the listener receives.
			 *     1. The PacketPlayerConnect packet is received when someone joins the game. If the packet contains the
			 *     same player that the packet was sent to then the player and their id are added to the players map.
			 *     Otherwise a new player is created and added to the map. A new sprite is created for the new player.
			 *     2. The PacketSendCoordinates packet is received when a player moves in the game. Next the
			 *     moving players coordinates are set accordingly.
			 * </p>
			 * @param connection (TCP or UDP)
			 * @param object that is received
			 */
			@Override
			public void received(Connection connection, Object object) {
				if (object instanceof PacketPlayerConnect) {
					if (((PacketPlayerConnect) object).getPlayerID() == connection.getID()) {
						players.put(connection.getID(), myPlayer);
					} else {
						Player player = new Player(((PacketPlayerConnect) object).getPlayerName());
						players.put(((PacketPlayerConnect) object).getPlayerID(), player);
						playScreen.createNewSprite(player);
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

	public int getPlayerSpriteId() {
		return playerSpriteId;
	}

	public void setPlayerSpriteId(int playerSpriteId) {
		this.playerSpriteId = playerSpriteId;
	}

	public Player getMyPlayer() {
		return myPlayer;
	}

	/**
	 * Send players position info to server.
	 * <p>
	 * 	The method uses the packet PacketSendCoordinates, sets the right x, y and player id values to the packet and
	 * 	sends it to the server.
	 * </p>
	 */
	public void sendPositionInfoToServer() {
		PacketSendCoordinates packetSendCoordinates = new PacketSendCoordinates();
		packetSendCoordinates.setX(myPlayer.getSprite().getB2body().getPosition().x);
		packetSendCoordinates.setY(myPlayer.getSprite().getB2body().getPosition().y);
		packetSendCoordinates.setPlayerID(client.getID());
		client.sendUDP(packetSendCoordinates);
	}

	/**
	 * Send packet with lobby id and player name to server.
	 * @param packet
	 */
	public void sendPacketToServer(Object packet) {
		client.sendUDP(packet);
	}

	/**
	 * Dispose of the client and batch.
	 */
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
