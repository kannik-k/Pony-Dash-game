package ee.taltech.pony_dash_for_spikes_salvation.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ee.taltech.pony_dash_for_spikes_salvation.Main;

public class PlayScreen implements Screen {
    private final Main game;
    // public static Texture texture = null; // ajutine
    private final OrthographicCamera gameCam;
    private final Viewport gamePort;

    // Tiled
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2d muutujad
    private World world;

    public PlayScreen(Main game){
        this.game = game;
        // texture = new Texture("twilight_sparkle_one.png");
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(400, 256, gameCam);

        // Loading map
        mapLoader = new TmxMapLoader();
        map  = mapLoader.load("testmap..tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
    }

    public void update(float dt) {
        gameCam.update();
        renderer.setView(gameCam);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor((float)0.941, (float)0.698, (float)0.784, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        game.batch.setProjectionMatrix(gameCam.combined); // Renderdab pildi kaameraga kaasa
        game.batch.begin(); // Opens window
        game.makeAllPlayersMove();
        game.makePlayerMove();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
