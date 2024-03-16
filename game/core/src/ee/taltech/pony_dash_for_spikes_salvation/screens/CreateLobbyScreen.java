package ee.taltech.pony_dash_for_spikes_salvation.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.lobby.Lobby;
import ee.taltech.pony_dash_for_spikes_salvation.packets.PacketLobby;

public class CreateLobbyScreen implements Screen {
    private final Main game;
    private final Stage stage;
    private final OrthographicCamera gameCam;
    private final ExtendViewport viewport;
    private SpriteBatch spriteBatch;
    private final Texture backgroundTexture;

    /**
     * Constructor.
     *
     * @param game Main game.
     */
    public CreateLobbyScreen(Main game) {
        this.game = game;
        spriteBatch = game.getBatch();
        backgroundTexture = new Texture("Game Assets/Sunny land winter forest sky.png");
        gameCam = new OrthographicCamera();
        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), gameCam);
        stage = new Stage(viewport, spriteBatch);
    }

    /**
     * Change cursor to pointer.
     */
    private void changeCursorToPointer() {
        Pixmap originalPixmap = new Pixmap(Gdx.files.internal("cursor-png-1115.png")); // Replace with your pointer image path
        int desiredWidth = 32; // Desired width of the cursor
        int desiredHeight = 32; // Desired height of the cursor
        Pixmap resizedPixmap = new Pixmap(desiredWidth, desiredHeight, originalPixmap.getFormat());
        resizedPixmap.drawPixmap(originalPixmap, 0, 0, originalPixmap.getWidth(), originalPixmap.getHeight(), 0, 0, desiredWidth, desiredHeight);
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(resizedPixmap, 0, 0));
        originalPixmap.dispose();
        resizedPixmap.dispose();
    }

    /**
     * Change cursor to default.
     */
    private void changeCursorToDefault() {
        Pixmap originalPixmap = new Pixmap(Gdx.files.internal("cursor-png-1127.png")); // Replace with your default cursor image path
        int desiredWidth = 32; // Desired width of the cursor
        int desiredHeight = 32; // Desired height of the cursor
        Pixmap resizedPixmap = new Pixmap(desiredWidth, desiredHeight, originalPixmap.getFormat());
        resizedPixmap.drawPixmap(originalPixmap, 0, 0, originalPixmap.getWidth(), originalPixmap.getHeight(), 0, 0, desiredWidth, desiredHeight);
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(resizedPixmap, 0, 0));
        originalPixmap.dispose();
        resizedPixmap.dispose();
    }

    @Override
    public void show() {
        Skin skin;
        spriteBatch = new SpriteBatch();

        skin = new Skin(Gdx.files.internal("Skin/terramotherui/terra-mother-ui.json"));

        // Tabel nuppude jaoks ekraani keskele
        Table centerTable = new Table();
        centerTable.setFillParent(true);
        stage.addActor(centerTable);

        TextButton name = new TextButton("Name", skin);
        name.setColor(Color.WHITE); // Määra tekstinupu tekstivärv valgeks
        centerTable.row();
        centerTable.add(name).pad(10).fillX().uniformX().left();

        TextButton createLobby = new TextButton("Create lobby", skin);
        createLobby.setColor(Color.WHITE); // Määra tekstinupu tekstivärv valgeks
        centerTable.row();
        centerTable.add(createLobby).pad(10).fillX().uniformX().left();

        TextButton joinLobby = new TextButton("Join lobby", skin);
        joinLobby.setColor(Color.WHITE); // Määra tekstinupu tekstivärv valgeks
        centerTable.row();
        centerTable.add(joinLobby).pad(10).fillX().uniformX().left();

        TextField lobbyIdField = new TextField("", skin);
        lobbyIdField.setColor(Color.WHITE); // Määra tekstivälja taustavärv valgeks
        centerTable.row();
        centerTable.add(lobbyIdField).pad(10).fillX().uniformX().left();


        Table backTable = new Table();
        TextButton back = new TextButton("Back", skin);
        backTable.top().left();
        backTable.setFillParent(true);
        backTable.add(back).pad(10);

        stage.addActor(backTable);

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
                changeCursorToDefault();
            }
        });

        createLobby.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Lobby newLobby = new Lobby();
                newLobby.addPlayer(game.getMyPlayer().getPlayerName());
                newLobby.incrementLobbyId();
                PacketLobby packet = new PacketLobby(newLobby.getLobbyId(), newLobby.getPlayers());
                game.sendPacketToServer(packet);
                game.setScreen(new LobbyScreen(game));
                changeCursorToDefault();
            }
        });

        InputListener inputListener = new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                changeCursorToPointer();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                changeCursorToDefault();
            }
        };

        name.addListener(inputListener);
        createLobby.addListener(inputListener);
        joinLobby.addListener(inputListener);
        back.addListener(inputListener);

        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameCam.update();
        spriteBatch.setProjectionMatrix(gameCam.combined);

        spriteBatch.begin();

        spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        spriteBatch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void hide() {
        // Called when this screen is no longer the current screen
    }

    @Override
    public void pause() {
        // Called when the application is paused
    }

    @Override
    public void resume() {
        // Called when the application is resumed from paused state
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }
}
