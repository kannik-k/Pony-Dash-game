package ee.taltech.pony_dash_for_spikes_salvation.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import ee.taltech.pony_dash_for_spikes_salvation.Main;
import ee.taltech.pony_dash_for_spikes_salvation.packets.PlayerJoinPacket;

public class CreateLobbyScreen implements Screen {
    private final Main game;
    private final Stage stage;
    private final OrthographicCamera gameCam;
    private final ExtendViewport viewport;
    private SpriteBatch spriteBatch;
    private final Texture backgroundTexture;
    private int spriteId;
    private TextField playerNameTextField;
    private String playerName = "";
    private boolean isNameSelected;
    private boolean isPlayerSelected;

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
        this.spriteId = 1;
        this.isNameSelected = false;
        this.isPlayerSelected = false;
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

        // Tabel nuppude jaoks ekraani keskele
        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        stage.addActor(buttonTable);


        TextButton twilight = new TextButton("Twilight", skin);
        TextButton rainbow = new TextButton("Rainbow", skin);
        TextButton applejack = new TextButton("Applejack", skin);
        TextButton fluttershy = new TextButton("Fluttershy", skin);
        TextButton pinkie = new TextButton("Pinkie", skin);
        TextButton rarity = new TextButton("Rarity", skin);


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

        twilight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                spriteId = 1;
                isPlayerSelected = true;
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
                isPlayerSelected = true;
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
                isPlayerSelected = true;
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
                isPlayerSelected = true;
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
                isPlayerSelected = true;
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
                isPlayerSelected = true;
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

        TextButton joinLobby = new TextButton("Join lobby", skin);
        joinLobby.setColor(Color.WHITE); // Määra tekstinupu tekstivärv valgeks
        buttonTable.row();
        buttonTable.add(joinLobby).pad(10).fillX().uniformX().left();

        Table backTable = new Table();
        TextButton back = new TextButton("Back", skin);
        backTable.top().left();
        backTable.setFillParent(true);
        backTable.add(back).pad(10);

        stage.addActor(backTable);

        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playerName = playerNameTextField.getText();
                game.setPlayerName(playerName);
                changeCursorToDefault();
                isNameSelected = true;
                okButton.setStyle(greenStyle);
            }
        });

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
                changeCursorToDefault();
            }
        });

        joinLobby.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (isNameSelected && isPlayerSelected) {
                    PlayerJoinPacket packet = new PlayerJoinPacket();
                    packet.setUserName(playerName);
                    PlayScreen playScreen = game.getPlayScreen();
                    playScreen.updatePonyIdAndSprite(spriteId);
                    game.sendPacketToServer(packet);
                    Gdx.app.postRunnable(() -> {
                        game.setScreen(new LobbyScreen(game));
                        changeCursorToDefault();
                    });
                }
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

        joinLobby.addListener(inputListener);
        back.addListener(inputListener);
        okButton.addListener(inputListener);
        twilight.addListener(inputListener);
        rainbow.addListener(inputListener);
        applejack.addListener(inputListener);
        pinkie.addListener(inputListener);
        rarity.addListener(inputListener);
        fluttershy.addListener(inputListener);

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
