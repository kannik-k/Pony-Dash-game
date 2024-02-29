package ee.taltech.pony_dash_for_spikes_salvation.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.sprites.PonySprite;

public class PlayScreen implements Screen {
    private final Main game;
    private static final Texture texture = new Texture("twilight_sparkle_one.png");
    private TextureAtlas atlas;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 308;
    public static final float PPM = 100f; // pixels per meter
    private final OrthographicCamera gameCam;
    private final Viewport gamePort;

    // Tiled
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2d muutujad
    private World world;
    private Box2DDebugRenderer b2dr;
    private PonySprite player;

    public static float getPPM() {
        return PPM;
    }

    public static Texture getTexture() {
        return texture;
    }

    public PlayScreen(Main game){
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(WIDTH / PPM, HEIGHT / PPM, gameCam);

        atlas = new TextureAtlas("pony_sprites.pack");

        // Loading map
        mapLoader = new TmxMapLoader();
        map  = mapLoader.load("testmap..tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1 / PPM);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        player = new PonySprite(world, this);

        // Ajutine, tuleb hiljem ümber tõsta
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // Platform Layer 10
        MapLayer collisionLayerPlatform= map.getLayers().get(10);

        for (MapObject object : collisionLayerPlatform.getObjects()) {
            if (object instanceof PolygonMapObject) {
                Polygon rect = ((PolygonMapObject) object).getPolygon();

                Rectangle boundingRectangle = rect.getBoundingRectangle();

                bdef.position.set((boundingRectangle.x + boundingRectangle.width / 2) / PPM,
                        (boundingRectangle.y + boundingRectangle.height / 2) / PPM);

                shape.setAsBox(boundingRectangle.width / 2 / PPM, boundingRectangle.height / 2 / PPM);

                body = world.createBody(bdef);
                fdef.shape = shape;
                body.createFixture(fdef);
            }
        }

        // Ground, demporary
        MapLayer collisionLayerGround = map.getLayers().get(8);

        for (MapObject object : collisionLayerGround.getObjects()) {
            if (object instanceof PolygonMapObject) {
                Polygon rect = ((PolygonMapObject) object).getPolygon();

                Rectangle boundingRectangle = rect.getBoundingRectangle();

                bdef.position.set((boundingRectangle.x + boundingRectangle.width / 2) / PPM,
                        (boundingRectangle.y + boundingRectangle.height / 2) / PPM);

                shape.setAsBox(boundingRectangle.width / 2 / PPM, boundingRectangle.height / 2 / PPM);

                body = world.createBody(bdef);
                fdef.shape = shape;
                body.createFixture(fdef);
            }
        }
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public  void hanelInput() { //dt parameetrit on vb hiljem vaja, võtta demopäevaks ära
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.getB2body().applyLinearImpulse(new Vector2(0, 4f), player.getB2body().getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.getB2body().getLinearVelocity().x <= 2)
            player.getB2body().applyLinearImpulse(new Vector2(0.1f, 0), player.getB2body().getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.getB2body().getLinearVelocity().x >= -2)
            player.getB2body().applyLinearImpulse(new Vector2(-0.1f, 0), player.getB2body().getWorldCenter(), true);
    }

    public void update(float dt) {
        hanelInput();
        player.update(dt);
        gameCam.position.x = player.getB2body().getPosition().x;
        gameCam.position.y = player.getB2body().getPosition().y;
        world.step(1/60f, 6, 2);
        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void show() {
        //Will use later
    }

    @Override
    public void render(float delta) {

        update(delta);
        Gdx.gl.glClearColor((float)0.941, (float)0.698, (float)0.784, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        b2dr.render(world, gameCam.combined);
        renderer.render();
        b2dr.render(world, gameCam.combined);
        game.getBatch().setProjectionMatrix(gameCam.combined); // Renderdab pildi kaameraga kaasa
        game.getBatch().begin(); // Opens window
        player.draw(game.getBatch());
        game.makeAllPlayersMove();
        game.makePlayerMove();
        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {
        //Will use later
    }

    public TiledMap getMap() {
        return map;
    }

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
