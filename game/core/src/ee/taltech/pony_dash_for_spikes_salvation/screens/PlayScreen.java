package ee.taltech.pony_dash_for_spikes_salvation.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.Player;
import ee.taltech.pony_dash_for_spikes_salvation.items.Coin;
import ee.taltech.pony_dash_for_spikes_salvation.sprites.PonySprite;

import java.util.Map;

public class PlayScreen implements Screen {
    private final Main game;
    private TextureAtlas atlas;
    private static final int WIDTH = 620;
    private static final int HEIGHT = 408;
    private static final float PPM = 100f; // pixels per meter
    private final OrthographicCamera gameCam;
    private final Viewport gamePort;

    private int ponyId;

    // Tiled
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2d muutujad
    private World world;
    private Box2DDebugRenderer b2dr;
    private PonySprite player;

    /**
     * Gets ppm.
     *
     * @return the ppm
     */
    public static float getPPM() {
        return PPM;
    }

    /**
     * Instantiates a new Play screen.
     * Temporarily has body defining and collision.
     *
     * @param game the game
     */
    public PlayScreen(Main game){
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(WIDTH / PPM, HEIGHT / PPM, gameCam);

        atlas = new TextureAtlas("pony_sprites.pack");
        ponyId = game.getMyPlayer().getSpriteId();

        // Loading map
        mapLoader = new TmxMapLoader();
        map  = mapLoader.load("Pony_dash_for_spike_salvation_map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1f / PPM);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        player = new PonySprite(world, this, game.getMyPlayer(), ponyId);
        game.getMyPlayer().setSprite(player);

        // Ajutine, tuleb hiljem ümber tõsta
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // Ground, temporary
        for (RectangleMapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = (object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / PPM, (rectangle.getY() + rectangle.getHeight() / 2) / PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth() / 2 / PPM, rectangle.getHeight() / 2 / PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        for (RectangleMapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = (object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / PPM, (rectangle.getY() + rectangle.getHeight() / 2) / PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth() / 2 / PPM, rectangle.getHeight() / 2 / PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        // Coin
        for(RectangleMapObject object: map.getLayers().get(15).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Coin(world, map, rect);
        }
    }

    /**
     * Create new sprite.
     *
     * @param player the player
     */
    public void createNewSprite(Player player) {
        PonySprite sprite = new PonySprite(world, this, player, ponyId);
        player.setSprite(sprite);
    }

    /**
     * Gets atlas.
     *
     * @return the atlas
     */
    public TextureAtlas getAtlas() {
        return atlas;
    }

    /**
     * Handle input and define movements.
     */
    public  void handleInput() {
        Player myPlayer = game.getMyPlayer();
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && (player.getCurrentState().equals("run")
                || player.getCurrentState().equals("standing"))) {
            player.getB2body().applyLinearImpulse(new Vector2(0, 4.5f), player.getB2body().getWorldCenter(), true);
            myPlayer.setX(player.getB2body().getPosition().x);
            myPlayer.setY(player.getB2body().getPosition().y);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.getB2body().getLinearVelocity().x <= 2) {
            player.getB2body().applyLinearImpulse(new Vector2(0.1f, 0), player.getB2body().getWorldCenter(), true);
            myPlayer.setX(player.getB2body().getPosition().x);
            myPlayer.setY(player.getB2body().getPosition().y);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.getB2body().getLinearVelocity().x >= -2) {
            player.getB2body().applyLinearImpulse(new Vector2(-0.1f, 0), player.getB2body().getWorldCenter(), true);
            myPlayer.setX(player.getB2body().getPosition().x);
            myPlayer.setY(player.getB2body().getPosition().y);
        }
    }

    /**
     * Update screen.
     *
     * @param dt the dt
     */
    public void update(float dt) {
        player.update(dt);
        handleInput();
        updateAllPlayers(dt);

        float mapWidth = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class) / PPM;
        float mapHeight = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class) / PPM;

        float cameraX = MathUtils.clamp(player.getB2body().getPosition().x, gameCam.viewportWidth / 2, mapWidth - gameCam.viewportWidth / 2);
        float cameraY = MathUtils.clamp(player.getB2body().getPosition().y, gameCam.viewportHeight / 2, mapHeight - gameCam.viewportHeight / 2);

        gameCam.position.set(cameraX, cameraY, 0);
        world.step(1/60f, 6, 2);
        gameCam.update();
        renderer.setView(gameCam);
    }

    /**
     * Update all players.
     *
     * @param dt the dt
     */
    public void updateAllPlayers(float dt) {
        Map<Integer, Player> playerMap = game.getPlayers();
        for (Map.Entry<Integer, Player> set : playerMap.entrySet()) {
            set.getValue().getSprite().update(dt);
        }
    }

    @Override
    public void show() {
        //Will use later
    }

    /**
     * Render the play-screen (background, world and players).
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor((float)0.941, (float)0.698, (float)0.784, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        b2dr.render(world, gameCam.combined);
        renderer.render();
        // b2dr.render(world, gameCam.combined); renders box2drender lines
        game.getBatch().begin(); // Opens window
        update(delta);

        game.getBatch().setProjectionMatrix(gameCam.combined); // Renders the game-cam
        player.draw(game.getBatch());
        renderAllPlayers();

        game.getBatch().end();
        game.sendPositionInfoToServer();
    }

    private void renderAllPlayers() {
        for (Map.Entry<Integer, Player> entry : game.getPlayers().entrySet()) {
            Player currentPlayer = entry.getValue();
            if (currentPlayer.getSprite() != null) {
                currentPlayer.getSprite().update(Gdx.graphics.getDeltaTime());
                currentPlayer.getSprite().draw(game.getBatch());
            }
        }
    }

    /**
     * Updates the size of the viewport.
     *
     * @param width of viewport
     * @param height of viewport
     */
    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }


    @Override
    public void pause() {
        //Will use later
    }

    /**
     * Gets map.
     *
     * @return the map
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * Gets world.
     *
     * @return the world
     */
    public World getWorld() {
        return world;
    }

    @Override
    public void resume() {
        //Will use later
    }

    @Override
    public void hide() {
        //Will use later
    }

    @Override
    public void dispose() {
        //Will use later
    }
}
