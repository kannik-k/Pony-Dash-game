package ee.taltech.pony_dash_for_spikes_salvation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import ee.taltech.pony_dash_for_spikes_salvation.ai.NPC;
import ee.taltech.pony_dash_for_spikes_salvation.exceptions.ConnectionException;
import ee.taltech.pony_dash_for_spikes_salvation.packets.*;
import ee.taltech.pony_dash_for_spikes_salvation.screens.GameOverScreen;
import ee.taltech.pony_dash_for_spikes_salvation.screens.LobbyScreen;
import ee.taltech.pony_dash_for_spikes_salvation.screens.MenuScreen;
import ee.taltech.pony_dash_for_spikes_salvation.screens.PlayScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Main extends Game {
	public static final int V_WIDTH = 620;
	public static final int V_HEIGHT = 408;
	private SpriteBatch batch; // holds stuff, for example maps. One is enough.
	private BitmapFont font;
	private Client client;
	private final Map<Integer, Player> players = new HashMap<>();
	private int playerCount = 1;
	private final List<NPC> bots = new ArrayList<>();
	private Player myPlayer;
	private int playerSpriteId;
	private PlayScreen playScreen;
	private LobbyScreen lobbyScreen;
	private GameOverScreen gameOverScreen;
	private int gameId;
	private int playerId;
	private String playerName;
	private boolean singlePlayer;
	private Logger logger = Logger.getLogger(getClass().getName());
	public static final short DEFAULT_BIT = 1;
	public static final short CHAR_BIT = 2;
	public static final short KEY_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short COLLECTED_BIT = 16;
	public static final short SPIKE_2_BIT = 32;
	public static final short SPIKE_3_BIT = 64;
	public static final short FINISH_BIT = 128;
	public static final short STAGE_BLOCK_BIT = 512;
	public static final short APPLE_BIT = 256;
	public static final short CHERRY_BIT = 1024;

	private AssetManager manager;

	public float getSoundVolume() {
		return soundVolume;
	}

	public void setSoundVolume(float soundVolume) {
		this.soundVolume = soundVolume;
	}

	private float soundVolume = 0.1f;

	public SpriteBatch getBatch() {
		return batch;
	}

	public Map<Integer, Player> getPlayers() {
		return players;
	}

	public List<NPC> getBots() {
		return bots;
	}

	public PlayScreen getPlayScreen() {
		return playScreen;
	}
	public AssetManager getManager() {
		return manager;
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
		manager = new AssetManager();
		manager.load("Game Assets/Mlp Gameloft Background Music Extended.mp3", Music.class);
		manager.load("Game Assets/mixkit-video-game-health-recharge-2837.wav", Sound.class);
		manager.load("Game Assets/mixkit-bonus-earned-in-video-game-2058.wav", Sound.class);
		manager.load("Game Assets/mixkit-winning-a-coin-video-game-2069.wav", Sound.class);
		manager.load("Game Assets/mixkit-game-bonus-reached-2065.wav", Sound.class);
		manager.load("Game Assets/mixkit-player-losing-or-failing-2042.wav", Sound.class);
		manager.load("Game Assets/mixkit-small-hit-in-a-game-2072.wav", Sound.class);
		manager.load("Game Assets/yay-101soundboards.mp3", Sound.class);
		manager.finishLoading();
		myPlayer = new Player("player");
		playScreen = new PlayScreen(this);
		gameOverScreen = new GameOverScreen(this);
		lobbyScreen = new LobbyScreen(this);
		singlePlayer = false;
		MenuScreen menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
		try {
			client.connect(5000, "localhost", 8080, 8081); // Use this to play on local host
			// client.connect(5000, "193.40.255.33", 8080, 8081); // Use this to play on the school server
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage());
		}
		PacketPlayerConnect packetPlayerConnect = new PacketPlayerConnect();
		packetPlayerConnect.setPlayerName("player");
		packetPlayerConnect.setGameID(gameId);
		packetPlayerConnect.setId(playerSpriteId);
		client.sendTCP(packetPlayerConnect); // Send server info that client has connected
		client.addListener(new Listener.ThreadedListener(new Listener() {
			/**
			 * Create listener for different packets sent to the client.
			 * <p>
			 *     There are eleven kinds of packets that the listener receives.
			 *     1. PacketGameId
			 *     2. The PacketPlayerConnect packet is received when someone joins the game. If the packet contains the
			 *     same player that the packet was sent to then the player and their id are added to the players map.
			 *     Otherwise a new player is created and added to the map. A new sprite is created for the new player.
			 *     3. The PacketSendCoordinates packet is received when a player moves in the game. Next the
			 *     moving players coordinates are set accordingly.
			 *     4. PacketPowerUpTaken is received when someone else has collected a power up in the clients game.
			 *     5. OnStartGame is received when someone presses "Start game" button. If server sends client this
			 *     packet, the lobby screen is switched to the play screen and game id is saved for the player.
			 *     6. OnLobbyList is sent to the client every time someone joins lobby. It contains list of players in
			 *     the lobby.
			 *     7. PacketGameOver contains winner id and name. Id and name will be saved in class GameOverScreen. The
			 *     screen is switched to the GameOverScreen and winner's name is displayed.
			 *     8. The PacketOnSpawn packets are received after the player connects to the server. One packet contains
			 *     an npc-s id and tiled coordinates. Then a new npc is added.
			 *     9. PacketOnSpawnNpc is received after connecting to a game. This includes all of the bots' initial coordinates.
			 *     10. PacketOnMoveNpc is received when bots move.
			 *     11. PacketCaptured is received when the client has been captured by a bot. This includes the starting time of the capture.
			 * </p>
			 * @param connection (TCP or UDP)
			 * @param object that is received
			 */
			@Override
			public void received(Connection connection, Object object) {
				if (object instanceof PacketUpdateLobby) {
					lobbyScreen.updatePlayerCount(((PacketUpdateLobby) object).getLobbySize());
				}

				if (object instanceof PacketGameId) {
					myPlayer.setGameID(((PacketGameId) object).getGameId());
				}

				if (object instanceof PacketPlayerConnect) {
					if (((PacketPlayerConnect) object).getPlayerID() == connection.getID()) {
						players.put(connection.getID(), myPlayer);
						playerId = connection.getID();
						gameId = ((PacketPlayerConnect) object).getGameID();
						myPlayer.setGameID(gameId);
						myPlayer.setId(connection.getID());
						myPlayer.setId(((PacketPlayerConnect) object).getId());
					} else {
						Player player = new Player(((PacketPlayerConnect) object).getPlayerName());
						player.setId(((PacketPlayerConnect) object).getId());
						players.put(((PacketPlayerConnect) object).getPlayerID(), player);
						playScreen.createNewSprite(player);
					}
				}

				if (object instanceof PacketSendCoordinates) {
					Player player = players.get(((PacketSendCoordinates) object).getPlayerID());
					if (player != null && player != myPlayer) {
						player.setX(((PacketSendCoordinates) object).getX());
						player.setY(((PacketSendCoordinates) object).getY());
						player.setGameID(myPlayer.getGameID());
						player.setState(((PacketSendCoordinates) object).getState());
						player.setId(((PacketSendCoordinates) object).getSpriteId());
						System.out.println(((PacketSendCoordinates) object).getState());
						System.out.println(((PacketSendCoordinates) object).getSpriteId());
					}
				}

				if (object instanceof PacketPowerUpTaken) {
					playScreen.deletePowerUp(((PacketPowerUpTaken) object).getX(), ((PacketPowerUpTaken) object).getY());
				}

				if (object instanceof OnStartGame) {
					Gdx.app.postRunnable(new Runnable() {
						@Override
						public void run() {
							setScreen(playScreen);
							myPlayer.setGameID(((OnStartGame) object).getGameId());
							gameId = myPlayer.getGameID();
						}
					});
				}

				if (object instanceof OnLobbyList) {
					List<OnLobbyJoin> names = new ArrayList<>(((OnLobbyList) object).getPeers());
					logger.info("new player has joined");
					for (OnLobbyJoin name : names) {
						logger.info(name.getName());
					}
				}

				if (object instanceof PacketOnSpawnNpc) {
					final PacketOnSpawnNpc onSpawnNpc = (PacketOnSpawnNpc) object;
					Gdx.app.postRunnable(new Runnable() {
						@Override
						public void run() {
							addNpc(onSpawnNpc.getId(), onSpawnNpc.getTiledX(), onSpawnNpc.getTiledY());
						}
					});
				}

				if (object instanceof PacketOnNpcMove) {
					final PacketOnNpcMove move = (PacketOnNpcMove) object;
					Gdx.app.postRunnable(new Runnable() {
						@Override
						public void run() {
							changeNpcLocation(move.getNetId(), move.getTiledX(), move.getTiledY());
						}
					});
				}

				if (object instanceof PacketCaptured) {
					Gdx.app.postRunnable(new Runnable() {
						@Override
						public void run() {
							myPlayer.setCaptureTime(LocalDateTime.parse(((PacketCaptured) object).getTime()));
						}
					});
				}

				if (object instanceof PacketGameOver) {
					Gdx.app.postRunnable(new Runnable() {
						@Override
						public void run() {
							gameOverScreen.setWinnerName(((PacketGameOver) object).getPlayerName());
							setScreen(gameOverScreen);
						}
					});
				}
			}
		}));
	}

	private void addNpc(int id, int tiledX, int tiledY) {
		Sprite sprite = new Sprite(new Texture("derpy.png"));
		sprite.setSize(32, 32);
		NPC npc = new NPC(id, tiledX, tiledY, sprite);
		bots.add(npc);
	}

	private void changeNpcLocation(int netId, int tiledX, int tiledY) {
		for (NPC npc : bots) {
			if (npc.getId() == netId) {
				npc.setReceiveDifference(System.currentTimeMillis() - npc.getLastReceive()); // Save how many millisecond it has been between sent packets - for smooth rendering
				npc.setLastReceive(System.currentTimeMillis());
				npc.setMoveX(tiledX);
				npc.setMoveY(tiledY);
			}
		}
	}

	public int getPlayerSpriteId() {
		return playerSpriteId;
	}

	public void setPlayerSpriteId(int playerSpriteId) {
		this.playerSpriteId = playerSpriteId;
		myPlayer.setSpriteId(playerSpriteId);
	}

	public int getPlayerId() {
		return playerId;
	}

	public boolean isSinglePlayer() {
		return singlePlayer;
	}

	public void setSinglePlayer(boolean singlePlayer) {
		this.singlePlayer = singlePlayer;
	}

	public Player getMyPlayer() {
		return myPlayer;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
		this.myPlayer.setPlayerName(this.playerName);
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
		float box2DX = myPlayer.getSprite().getB2body().getPosition().x;
		float box2DY = myPlayer.getSprite().getB2body().getPosition().y;
		packetSendCoordinates.setX(box2DX);
		packetSendCoordinates.setY(box2DY);
		packetSendCoordinates.setTiledX(Math.round(box2DX * 100)); // PPM = 100
		packetSendCoordinates.setTiledY(Math.round(box2DY * 100));
		packetSendCoordinates.setPlayerID(client.getID());
		packetSendCoordinates.setGameID(myPlayer.getGameID());
		packetSendCoordinates.setSpriteId(myPlayer.getSpriteId());
		packetSendCoordinates.setState(myPlayer.getState());
		// logger.info("position info going to the server: sprite id " + String.valueOf(myPlayer.getSpriteId()));
		// logger.info("current state: " + myPlayer.getState());
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
		PacketPlayerLeftLobby packet = new PacketPlayerLeftLobby();
		packet.setId(myPlayer.getId());
		packet.setName(myPlayer.getPlayerName());
		this.sendPacketToServer(packet);
		client.close();
		try {
			client.dispose();
		} catch (IOException e) {
			throw new ConnectionException(e.getMessage());
		}
		batch.dispose();
		playerCount = 1;
		manager.dispose();
	}

	public int getPlayerCount() {
		return playerCount;
	}
}
