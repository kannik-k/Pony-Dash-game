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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.packets.PacketSinglePlayer;

public class PlayerSelection implements Screen {
    private final Main game;
    private Stage stage;
    private final OrthographicCamera gameCam;
    private ExtendViewport viewport;
    private SpriteBatch spriteBatch;
    private Texture backgroundTexture;
    private int spriteId;
    private TextField playerNameTextField;
    private String playerName = "";

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

        heading = new Label("Enter your name:\tSelect player:", skin);
        Table textTable = new Table();
        textTable.setFillParent(true);
        textTable.row().pad(0, 0, 220, 0);
        textTable.add(heading).fillX().uniformX();
        textTable.row();
        stage.addActor(textTable);

        // Create text field for entering player name
        playerNameTextField = new TextField("", skin);
        playerNameTextField.setMessageText("Enter your name");
        playerNameTextField.setSize(200, 585);
        float newX = stage.getWidth() / 2 - playerNameTextField.getWidth() / 2 - 38;
        playerNameTextField.setPosition(newX, playerNameTextField.getY());
        stage.addActor(playerNameTextField);

        TextButton okButton = new TextButton("OK", skin);

        Table okTable = new Table();
        okTable.setFillParent(true);
        stage.addActor(okTable);
        okTable.row().pad(-22, 0, 0, 150);
        okTable.add(okButton).fillX().uniformX();
        okTable.row();

        // Create button table for player selection
        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        stage.addActor(buttonTable);

        // Create buttons for selecting player
        TextButton twilight = new TextButton("Twilight", skin);
        TextButton rainbow = new TextButton("Rainbow", skin);
        TextButton applejack = new TextButton("Applejack", skin);
        TextButton fluttershy = new TextButton("Fluttershy", skin);
        TextButton pinkie = new TextButton("Pinkie", skin);
        TextButton rarity = new TextButton("Rarity", skin);
        TextButton start = new TextButton("Start", skin);

        // Add buttons to the button table
        buttonTable.row().pad(100, 168, 10, 0);
        buttonTable.add(twilight).fillX().uniformX();
        buttonTable.row().pad(0, 168, 10, 0);
        buttonTable.add(rainbow).fillX().uniformX();
        buttonTable.row().pad(0, 168, 10, 0);
        buttonTable.add(applejack).fillX().uniformX();
        buttonTable.row().pad(0, 168, 10, 0);
        buttonTable.add(fluttershy).fillX().uniformX();
        buttonTable.row().pad(0, 168, 10, 0);
        buttonTable.add(pinkie).fillX().uniformX();
        buttonTable.row().pad(0, 168, 10, 0);
        buttonTable.add(rarity).fillX().uniformX();

        // Create table for "Back" button
        Table backTable = new Table();
        TextButton back = new TextButton("Back", skin);
        backTable.top().left();
        backTable.setFillParent(true);
        backTable.add(back).pad(10);
        stage.addActor(backTable);

        // Create table for "Start" button
        Table startTable = new Table();
        startTable.setFillParent(true);
        stage.addActor(startTable);
        startTable.row().pad(0, 0, -400, 0);
        startTable.add(start).fillX().uniformX();
        startTable.row();

        // Add listeners to buttons
        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Name saved");
                playerName = playerNameTextField.getText();
                game.setPlayerName(playerName);
                game.getMyPlayer().setPlayerName(playerName);
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
                startGame();
            }
        });

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
                changeCursorToPointer();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                changeCursorToDefault();
            }
        };

        back.addListener(inputListener);
        okButton.addListener(inputListener);
        twilight.addListener(inputListener);
        rainbow.addListener(inputListener);
        applejack.addListener(inputListener);
        pinkie.addListener(inputListener);
        rarity.addListener(inputListener);
        fluttershy.addListener(inputListener);
        start.addListener(inputListener);

        // Set input processor
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Render the menu-screen (background and stage).
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(.1f, .1f, .15f, 1);  // screen color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the background image
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, stage.getWidth(), stage.getHeight());
        spriteBatch.end();

        // Render the stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /**
     * Updates the size of the viewport.
     *
     * @param width  Width of the viewport
     * @param height Height of the viewport
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Dispose of resources when the screen is hidden or closed.
     */
    @Override
    public void hide() {
        // Dispose of resources
        dispose();
    }

    /**
     * Pause method required by Screen interface.
     */
    @Override
    public void pause() {
        // Called when the application is paused
    }

    /**
     * Resume method required by Screen interface.
     */
    @Override
    public void resume() {
        // Called when the application is resumed from paused state
    }

    /**
     * Dispose of resources when the screen is closed.
     */
    @Override
    public void dispose() {
        // Dispose of stage and textures
        stage.dispose();
        backgroundTexture.dispose();
        spriteBatch.dispose();
    }

    /**
     * Change cursor to pointer.
     */
    private void changeCursorToPointer() {
        Pixmap originalPixmap = new Pixmap(Gdx.files.internal("cursor-png-1115.png"));
        int desiredWidth = 32;
        int desiredHeight = 32;
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
        Pixmap originalPixmap = new Pixmap(Gdx.files.internal("cursor-png-1127.png"));
        int desiredWidth = 32;
        int desiredHeight = 32;
        Pixmap resizedPixmap = new Pixmap(desiredWidth, desiredHeight, originalPixmap.getFormat());
        resizedPixmap.drawPixmap(originalPixmap, 0, 0, originalPixmap.getWidth(), originalPixmap.getHeight(), 0, 0, desiredWidth, desiredHeight);
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(resizedPixmap, 0, 0));
        originalPixmap.dispose();
        resizedPixmap.dispose();
    }

    /**
     * Method to start the game.
     */
    private void startGame() {

        // Send packet to server
        PacketSinglePlayer packet = new PacketSinglePlayer();
        game.setSinglePlayer(true);
        game.sendPacketToServer(packet);

        // Update player sprite ID
        PlayScreen playScreen = game.getPlayScreen();
        playScreen.updatePonyIdAndSprite(spriteId);

        // Set the play screen
        game.setScreen(playScreen);

        // Change cursor to default
        changeCursorToDefault();
    }
}
