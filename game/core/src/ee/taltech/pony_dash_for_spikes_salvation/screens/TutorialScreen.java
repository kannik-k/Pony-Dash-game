package ee.taltech.pony_dash_for_spikes_salvation.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
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

public class TutorialScreen implements Screen {
    private final Main game;
    private final Stage stage;
    private final Texture backgroundTexture;
    private static final String TUTORIAL_TEXT = "We are delighted to greet you on this Ponyville adventure!\n\n" +
            "Spike, our beloved dragon, has been kidnapped and imprisoned, and his rescue depends on you!" +
            "Your task is to reach the end point as quickly as possible to free Spike from his dire situation." +
            "But proceed with caution, as the path is filled with dangerous obstacles, such as spikes, which you must avoid falling onto!\n" +
            "The game consists of three exciting stages, with checkpoints along the way that you can pass through by collecting coins." +
            "To rescue Spike, you need to find the key hidden in the third and final part of the map.\n\n" +
            "Instructions:\n\n" +
            "Press the up arrow to jump.\n" +
            "Use the right arrow to run to the right.\n" +
            "Use the left arrow to run to the left.\n\n" +
            "Get ready for thrilling adventures and remember, your speed and skill are the key to Spike's freedom!";

    /**
     * Constructor.
     *
     * @param game  Main game.
     */
    public TutorialScreen(Main game) {
        this.game = game;
        backgroundTexture = new Texture("Game Assets/Sunny land winter forest sky.png");
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    /**
     * Create everything that is shown on the menu screen.
     */
    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("Skin/terramotherui/terra-mother-ui.json"));

        // Esimene lause eraldi Label'ina
        Label.LabelStyle firstSentenceStyle = skin.get("default", Label.LabelStyle.class);
        Label firstSentenceLabel = new Label("Welcome to Poni Dash for Spike's Salvation!", firstSentenceStyle);

        // Teksti lause
        Label.LabelStyle labelStyle = skin.get("default", Label.LabelStyle.class);
        Label tutorialLabel = new Label(TUTORIAL_TEXT, labelStyle);
        tutorialLabel.setWrap(true);
        tutorialLabel.setWidth(600);

        float labelX = (stage.getWidth() - tutorialLabel.getWidth()) / 2f;
        float labelY = 85;
        tutorialLabel.setPosition(labelX, labelY);

        TextButton back = new TextButton("Back", skin);

        Table topTable = new Table();
        topTable.top().left();
        topTable.setFillParent(true);
        topTable.add(back).pad(10).center();
        topTable.add(firstSentenceLabel).pad(0, 90, 0, 0).center();

        stage.addActor(topTable);

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
                changeCursorToDefault();
            }
        });

        InputListener inputListener = new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                // Change cursor to pointer when mouse enters the button
                changeCursorToPointer();
            }
        };

        stage.addActor(tutorialLabel);

        back.addListener(inputListener);

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Render the menu-screen (background and stage).
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .1f, .15f, 1);  // screen color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(backgroundTexture, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();

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
     * Dispose of stage and background texture when the screen is closed.
     */
    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }
}
