package ee.taltech.pony_dash_for_spikes_salvation.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
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

public class PlayerSelection implements Screen {
    private final Main game;
    private Stage stage;
    private final OrthographicCamera gameCam;
    private ExtendViewport viewport;
    private SpriteBatch spriteBatch;
    private Texture backgroundTexture;
    private int spriteId;

    /**
     * Constructor.
     *
     * @param game  Main game.
     */
    public PlayerSelection(Main game) {
        this.game = game;
        spriteBatch = game.getBatch();
        backgroundTexture = new Texture("Game Assets/Sunny land winter forest sky.png");
        changeCursorToDefault();
        gameCam = new OrthographicCamera();
        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), gameCam);
        stage = new Stage(viewport);
        this.spriteId = 1;
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

        TextButton.TextButtonStyle defaultStyle = skin.get("default", TextButton.TextButtonStyle.class);
        TextButton.TextButtonStyle greenStyle = new TextButton.TextButtonStyle(defaultStyle);
        greenStyle.fontColor = Color.GREEN;

        heading = new Label("Select player:", skin);
        Table textTable = new Table();
        textTable.setFillParent(true);
        textTable.row().pad(0, 0, 220, 0);
        textTable.add(heading).fillX().uniformX();
        textTable.row();
        stage.addActor(textTable);

        // Table for the buttons visible on screen.
        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        stage.addActor(buttonTable);


        TextButton twilight = new TextButton("Twilight", skin);
        TextButton rainbow = new TextButton("Rainbow", skin);
        TextButton applejack = new TextButton("Applejack", skin);
        TextButton fluttershy = new TextButton("Fluttershy", skin);
        TextButton pinkie = new TextButton("Pinkie", skin);
        TextButton rarity = new TextButton("Rarity", skin);
        TextButton start = new TextButton("Start", skin);


        buttonTable.row().pad(100, 0, 10, 0);
        buttonTable.add(twilight).fillX().uniformX();
        buttonTable.row().pad(0, 0, 10, 0);
        buttonTable.add(rainbow).fillX().uniformX();
        buttonTable.row().pad(0, 0, 10, 0);
        buttonTable.add(applejack).fillX().uniformX();
        buttonTable.row().pad(0, 0, 10, 0);
        buttonTable.add(fluttershy).fillX().uniformX();
        buttonTable.row().pad(0, 0, 10, 0);
        buttonTable.add(pinkie).fillX().uniformX();
        buttonTable.row().pad(0, 0, 10, 0);
        buttonTable.add(rarity).fillX().uniformX();

        Table backTable = new Table();
        TextButton back = new TextButton("Back", skin);
        backTable.top().left();
        backTable.setFillParent(true);
        backTable.add(back).pad(10);

        stage.addActor(backTable);

        Table startTable = new Table();
        startTable.setFillParent(true);
        stage.addActor(startTable);

        startTable.row().pad(0, 0, -400, 0);
        startTable.add(start).fillX().uniformX();
        startTable.row();

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
                changeCursorToDefault();
            }
        });

        twilight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                spriteId = 1;
                twilight.setStyle(greenStyle);
                rainbow.setStyle(defaultStyle);
                applejack.setStyle(defaultStyle);
                pinkie.setStyle(defaultStyle);
                rarity.setStyle(defaultStyle);
                fluttershy.setStyle(defaultStyle);
                game.setPlayerSpriteId(spriteId);
                changeCursorToDefault();
            }
        });

        rainbow.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                spriteId = 2;
                twilight.setStyle(defaultStyle);
                rainbow.setStyle(greenStyle);
                applejack.setStyle(defaultStyle);
                pinkie.setStyle(defaultStyle);
                rarity.setStyle(defaultStyle);
                fluttershy.setStyle(defaultStyle);
                game.setPlayerSpriteId(spriteId);
                changeCursorToDefault();
            }
        });

        applejack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                spriteId = 3;
                twilight.setStyle(defaultStyle);
                rainbow.setStyle(defaultStyle);
                applejack.setStyle(greenStyle);
                pinkie.setStyle(defaultStyle);
                rarity.setStyle(defaultStyle);
                fluttershy.setStyle(defaultStyle);
                game.setPlayerSpriteId(spriteId);
                changeCursorToDefault();
            }
        });

        pinkie.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                spriteId = 4;
                twilight.setStyle(defaultStyle);
                rainbow.setStyle(defaultStyle);
                applejack.setStyle(defaultStyle);
                pinkie.setStyle(greenStyle);
                rarity.setStyle(defaultStyle);
                fluttershy.setStyle(defaultStyle);
                game.setPlayerSpriteId(spriteId);
                changeCursorToDefault();
            }
        });

        rarity.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                spriteId = 5;
                twilight.setStyle(defaultStyle);
                rainbow.setStyle(defaultStyle);
                applejack.setStyle(defaultStyle);
                pinkie.setStyle(defaultStyle);
                rarity.setStyle(greenStyle);
                fluttershy.setStyle(defaultStyle);
                game.setPlayerSpriteId(spriteId);
                changeCursorToDefault();
            }
        });

        fluttershy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                spriteId = 6;
                twilight.setStyle(defaultStyle);
                rainbow.setStyle(defaultStyle);
                applejack.setStyle(defaultStyle);
                pinkie.setStyle(defaultStyle);
                rarity.setStyle(defaultStyle);
                fluttershy.setStyle(greenStyle);
                game.setPlayerSpriteId(spriteId);
                changeCursorToDefault();
            }
        });

        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setPlayerSpriteId(spriteId);
                PlayScreen playScreen = game.getPlayScreen();
                playScreen.updatePonyIdAndSprite(spriteId);
                game.setScreen(playScreen);
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

        twilight.addListener(inputListener);
        rainbow.addListener(inputListener);
        applejack.addListener(inputListener);
        pinkie.addListener(inputListener);
        rarity.addListener(inputListener);
        fluttershy.addListener(inputListener);
        start.addListener(inputListener);

        Gdx.input.setInputProcessor(stage);
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
