package ee.taltech.pony_dash_for_spikes_salvation.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import ee.taltech.pony_dash_for_spikes_salvation.Main;


public class MenuScreen implements Screen {
    private final Main game;
    private Stage stage;
    private final OrthographicCamera gameCam;
    private ExtendViewport viewport;
    private SpriteBatch spriteBatch;
    private Texture backgroundTexture;


    /**
     * Constructor.
     * @param game Main game.
     */
    public MenuScreen(Main game) {
        this.game = game;
        spriteBatch = game.getBatch();
        backgroundTexture = new Texture("Game Assets/Sunny land winter forest sky.png");
        changeCursorToDefault();
        gameCam = new OrthographicCamera();
        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), gameCam);
        stage = new Stage(viewport);
    }

    /**
     * Render the menu-screen (background and stage).
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(.1f, .1f, .15f, 1);  // screen color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the background image
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, stage.getWidth(), stage.getHeight());
        spriteBatch.end();

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /**
     * Updates the size of the viewport.
     *
     * @param width of viewport
     * @param height of viewport
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

    /**
     * Create everything that is shown on the menu screen.
     * <p>
     *  A button table is added to the stage. This includes the heading and three buttons.
     *  Listeners for buttons are also added.
     * </p>
     */
    @Override
    public void show() {
        Skin skin;
        Label heading;
        spriteBatch = new SpriteBatch();

        skin = new Skin(Gdx.files.internal("Skin/terramotherui/terra-mother-ui.json"));
        heading = new Label("Welcome to Pony Dash For Spikes Salvation!", skin);
        Table textTable = new Table();
        textTable.setFillParent(true);
        textTable.row().pad(0, 0, 110, 0);
        textTable.add(heading).fillX().uniformX();
        textTable.row();
        stage.addActor(textTable);

        // Table for the buttons visible on screen.
        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        stage.addActor(buttonTable);


        TextButton singlePlayer = new TextButton("Single player", skin);
        TextButton multiplayer = new TextButton("Multiplayer", skin);
        TextButton exit = new TextButton("Exit", skin);


        buttonTable.row().pad(100, 0, 10, 0);
        buttonTable.add(singlePlayer).fillX().uniformX();
        buttonTable.row().pad(0, 0, 10, 0);
        buttonTable.add(multiplayer).fillX().uniformX();
        buttonTable.row();
        buttonTable.add(exit).fillX().uniformX();

        // create button listeners
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        singlePlayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PlayScreen(game));
                changeCursorToDefault();
            }
        });

        multiplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //This button will lead to the multiplayer lobby
            }
        });

        InputListener inputListener = new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                // Change cursor to pointer when mouse enters the button
                changeCursorToPointer();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                // Change cursor back to default when mouse exits the button
                changeCursorToDefault();
            }
        };

        exit.addListener(inputListener);
        singlePlayer.addListener(inputListener);
        multiplayer.addListener(inputListener);

        Gdx.input.setInputProcessor(stage);
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

    /**
     * Dispose of stage, background and spriteBatch when the menu-screen is closed.
     */
    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        spriteBatch.dispose();

        Gdx.input.setInputProcessor(null);
    }
}
